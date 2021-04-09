package com.fluffy.spring.controllers;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.IconStorageService;
import com.fluffy.spring.services.UserService;
import com.fluffy.spring.util.FileUploadUtil;
import com.fluffy.spring.validation.forms.UserDataForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class UserController {
    private final Validator userDataFormValidator;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ServletContext servletContext;
    private final IconStorageService iconStorageService;

    public UserController(@Qualifier("userDataFormValidator") Validator userDataFormValidator,
                          UserService userService,
                          UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          ServletContext servletContext,
                          IconStorageService iconStorageService) {
        this.userDataFormValidator = userDataFormValidator;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.servletContext = servletContext;
        this.iconStorageService = iconStorageService;
    }

    @InitBinder("userDataForm")
    protected void userDataFormInitBinder(WebDataBinder binder) {
        binder.setValidator(userDataFormValidator);
    }

    @GetMapping("/profile")
    public String profileView(ModelMap modelMap) {
        User user = userService.findByEmail(AuthController.getCurrentUsername());
        modelMap.addAttribute("userDataForm", new UserDataForm());
        modelMap.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public ModelAndView profileUpdate(
            @Valid @ModelAttribute(name = "userDataForm") UserDataForm userDataForm,
            BindingResult bindingResult,
            @RequestParam("icon") MultipartFile multipartFile
            ) {
        // оновлені дані про поточного користувача (без userId, rday)
        User currentUser = userDataForm.buildUser(passwordEncoder);

        // описує стан поточного користувача у минулому
        User user = userService.findByEmail(AuthController.getCurrentUsername());

        // треба перенести деякі дані, щоб об'єкт currentUser міг представляти
        // поточного користувача (TODO: +icon - під час оновлення даної ділянки
        // коду)
        currentUser.setId(user.getId());
        currentUser.setEnabled(user.isEnabled());
        currentUser.setRday(user.getRday());



        iconStorageService.store(multipartFile);
        currentUser.setIcon(multipartFile.getOriginalFilename());

        System.out.println(multipartFile.getOriginalFilename());
        System.out.println(currentUser.getIcon());

        if (!bindingResult.hasErrors()) {
            // файл завантажується лише тоді, коли всі інші дані не містять
            // помилок - імітується "транзакція", її атомарність
            

            // під час проходження валідації помилок не виникало - треба
            // оновити дані про поточного користувача

            String password = currentUser.getPassword();
            String newPassword = userDataForm.getNewPassword();

            // новий пароль не порожній та помилок не було зафіксовано - отже
            // користувач міг змінити пароль
            if (newPassword != null && !newPassword.isEmpty()) {
                password = passwordEncoder.encode(newPassword);
                currentUser.setPassword(password);
            }

            // відповідні зміни в persistence-шарі веб-додатку
            userService.update(currentUser.getId(), currentUser);

            // користувач міг змінити дані, необхідні для проходження
            // авторизації, тому треба виконати додаткові налаштування
            String email = currentUser.getEmail();

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities()));
        }

        System.out.println(bindingResult.getFieldErrors());

        return new ModelAndView("profile", "user", currentUser);
    }
}

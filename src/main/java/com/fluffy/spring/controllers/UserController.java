package com.fluffy.spring.controllers;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import com.fluffy.spring.util.FileUploadUtil;
import com.fluffy.spring.validation.forms.UserDataForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UserController {
    private final Validator userDataFormValidator;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ServletContext servletContext;
    private final Environment env;

    public UserController(@Qualifier("userDataFormValidator") Validator userDataFormValidator,
                          UserService userService,
                          UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          ServletContext servletContext,
                          Environment env) {
        this.userDataFormValidator = userDataFormValidator;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.servletContext = servletContext;
        this.env = env;
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
            BindingResult bindingResult
            ) throws IOException {
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

        // щоб уникнути зміну зображення, якщо та не відбулася
        currentUser.setIcon(user.getIcon());

        if (!bindingResult.hasErrors()) {
            // видалити попередній файл (и) ...

            // файл завантажується лише тоді, коли всі інші дані не містять
            // помилок - імітується "транзакція", її атомарність



            /*
            MultipartFile multipartFile = userDataForm.getIcon();
            String fileName = multipartFile.getOriginalFilename();

            String uploadPath = servletContext.getRealPath(env.getProperty("application.resources.upload-path")) + File.separator + currentUser.getId();
            String visibleUploadPath = File.separator + fileName

            Path path = Paths.get(uploadPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            //uploadPath += File.separator + multipartFile.getOriginalFilename();
            FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadPath));
            currentUser.setIcon(uploadPath);
             */

            MultipartFile multipartFile = userDataForm.getIcon();
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            currentUser.setIcon(fileName);

            String uploadDir = "icons/" + currentUser.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);



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

        return new ModelAndView("profile", "user", currentUser);
    }
}

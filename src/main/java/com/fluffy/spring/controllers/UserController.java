package com.fluffy.spring.controllers;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import com.fluffy.spring.validation.forms.UserDataForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {
    private final Validator userDataFormValidator;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public UserController(@Qualifier("userDataFormValidator") Validator userDataFormValidator,
                          UserService userService,
                          UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDataFormValidator = userDataFormValidator;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
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
    ) {
        User updatedUser = userDataForm.buildUser(passwordEncoder);
        if (!bindingResult.hasErrors()) {
            User user = userService.findByEmail(AuthController.getCurrentUsername());
            user.setEmail(updatedUser.getEmail());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            userService.update(user.getId(), user);

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), userDetails.getAuthorities()));

            User u = userService.findByEmail(AuthController.getCurrentUsername());
            return new ModelAndView("profile", "user", u);
        }
        User user = userService.findByEmail(AuthController.getCurrentUsername());
        user.setEmail(updatedUser.getEmail());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        // для повернення помилок та підписів
        return new ModelAndView("profile", "user", user);
    }
}

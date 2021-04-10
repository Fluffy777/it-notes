package com.fluffy.spring.controllers;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import com.fluffy.spring.validation.forms.users.LogInForm;
import com.fluffy.spring.validation.forms.users.SignUpForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final Validator signUpFormValidator;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(@Qualifier("signUpFormValidator") Validator signUpValidator,
                          UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.signUpFormValidator = signUpValidator;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @InitBinder("signUpForm")
    protected void signUpFormInitBinder(WebDataBinder binder) {
        binder.setValidator(signUpFormValidator);
    }

    @GetMapping("/login")
    public String logInView(@RequestParam(name = "error", required = false) boolean error, ModelMap modelMap) {
        modelMap.addAttribute("logInForm", new LogInForm());
        if (error) {
            modelMap.addAttribute("error", true);
        }
        return "/auth/login";
    }

    @GetMapping("/signup")
    public String signUpView(ModelMap modelMap) {
        modelMap.addAttribute("signUpForm", new SignUpForm());
        return "/auth/signup";
    }

    @PostMapping("/signup")
    public String signUp(
            @Valid @ModelAttribute(name = "signUpForm") SignUpForm signUpForm,
            BindingResult result) {
        if (result.hasErrors()) {
            return "/auth/signup";
        }

        userService.create(signUpForm.buildUser(passwordEncoder));
        return "redirect:/login";
    }

    public static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof User) {
            return ((User) principal).getEmail();
        } else {
            return principal.toString();
        }
    }
}

package com.fluffy.spring.controllers;

import com.fluffy.spring.services.UserService;
import com.fluffy.spring.util.ConversionUtils;
import com.fluffy.spring.validation.forms.LogInForm;
import com.fluffy.spring.validation.forms.SignUpForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final Validator signUpFormValidator;
    //private final Validator logInFormValidator;
    private final UserService userService;

    public AuthController(@Qualifier("signUpFormValidator") Validator signUpValidator/*,
                          @Qualifier("logInFormValidator") Validator logInValidator*/,
                          UserService userService) {
        this.signUpFormValidator = signUpValidator;
        //this.logInFormValidator = logInValidator;
        this.userService = userService;
    }

    @InitBinder
    protected void signUpFormInitBinder() {

    }

    @GetMapping("/login")
    public String logInView(ModelMap modelMap) {
        modelMap.addAttribute("logInForm", new SignUpForm());
        return "/auth/login";
    }

    @GetMapping("/signup")
    public String signUpView(ModelMap modelMap) {
        modelMap.addAttribute("signUpForm", new SignUpForm());
        return "/auth/signup";
    }

    @PostMapping("signup/request")
    public String signUp(
            @Validated @ModelAttribute("signUpForm") SignUpForm signUpForm,
            BindingResult result) {
        if (result.hasErrors()) {
            return "/auth/signup";
        } else if (userService.findByEmail(signUpForm.getEmail()) != null) {
            result.addError(new FieldError("signUpForm",
                    "email",
                    "application.validation.sign-up-form.error-code.email-already-exists"));
            return "/auth/signup";
        }

        userService.create(ConversionUtils.convert(signUpForm));
        return "redirect:/login";
    }

    @PostMapping("login/request")
    @ResponseBody
    public String logIn(
            @Validated @ModelAttribute("logInForm") LogInForm logInForm,
            BindingResult result) {
        return "Cool!";
    }
}

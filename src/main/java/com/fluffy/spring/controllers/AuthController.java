package com.fluffy.spring.controllers;

import com.fluffy.spring.validation.forms.LogInForm;
import com.fluffy.spring.validation.forms.SignUpForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final Validator signUpFormValidator;
    //private final Validator logInFormValidator;

    public AuthController(@Qualifier("signUpFormValidator") Validator signUpValidator/*,
                          @Qualifier("logInFormValidator") Validator logInValidator*/) {
        this.signUpFormValidator = signUpValidator;
        //this.logInFormValidator = logInValidator;
    }

    @InitBinder
    protected void signUpFormInitBinder() {

    }

    @GetMapping("/login")
    public String logInView() {
        return "/auth/login";
    }

    @GetMapping("/signup")
    public String signUpView(ModelMap modelMap) {
        modelMap.addAttribute("signUpForm", new SignUpForm());
        return "/auth/signup";
    }

    @ResponseBody
    @PostMapping("signup/request")
    public String signUp(@Validated @ModelAttribute("signUpForm") SignUpForm signUpForm) {
        return "Good!";
    }
}

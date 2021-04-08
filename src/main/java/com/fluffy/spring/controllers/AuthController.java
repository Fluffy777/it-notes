package com.fluffy.spring.controllers;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import com.fluffy.spring.util.ConversionUtils;
import com.fluffy.spring.validation.forms.LogInForm;
import com.fluffy.spring.validation.forms.SignUpForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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
            System.out.println("has errors!");
            System.out.println(result.getFieldErrors());
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

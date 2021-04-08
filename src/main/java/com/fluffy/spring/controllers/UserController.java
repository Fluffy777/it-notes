package com.fluffy.spring.controllers;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import com.fluffy.spring.validation.forms.UserDataForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ModelAndView profileView(ModelMap modelMap) {
        User user = userService.findByEmail(AuthController.getCurrentUsername());
        modelMap.addAttribute("userDataForm", new UserDataForm());
        return new ModelAndView("profile", "user", user);
    }
}

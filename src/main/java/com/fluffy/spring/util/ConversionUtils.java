package com.fluffy.spring.util;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.validation.forms.LogInForm;
import com.fluffy.spring.validation.forms.SignUpForm;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class ConversionUtils {
    private ConversionUtils() { }

    public static User convert(LogInForm logInForm) {
        User user = new User();
        user.setEmail(logInForm.getEmail());
        user.setPassword(logInForm.getPassword());
        return user;
    }

    public static User convert(SignUpForm signUpForm) {
        User user = new User();
        user.setFirstName(signUpForm.getFirstName());
        user.setLastName(signUpForm.getLastName());
        user.setGender(signUpForm.getGender().equals("F") ? User.Gender.FEMALE : User.Gender.MALE);
        user.setEmail(signUpForm.getEmail());
        user.setPassword(signUpForm.getPassword());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        user.setBday(Date.valueOf(LocalDate.parse(signUpForm.getBday(), formatter)));

        user.setAddress(signUpForm.getAddress());
        return user;
    }
}

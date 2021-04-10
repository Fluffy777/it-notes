package com.fluffy.spring.validation.validators.users;

import com.fluffy.spring.validation.forms.users.LogInForm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Service
public class LogInFormValidator extends AbstractFormValidator {
    public LogInFormValidator(Environment env) {
        super(env);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(LogInForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LogInForm logInForm = (LogInForm) target;

        // перевірка на непорожність
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "logInForm.email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "logInForm.password.empty");

        // перевірка на коректність
        validate(InputType.EMAIL, logInForm.getEmail(), errors, "email", "logInForm.email.invalid");
        validate(InputType.PASSWORD, logInForm.getPassword(), errors, "password", "logInForm.password.invalid");
    }
}

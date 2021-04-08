package com.fluffy.spring.validation.validators;

import com.fluffy.spring.validation.forms.LogInForm;
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
    public boolean supports(Class<?> aClass) {
        return aClass.equals(LogInForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        LogInForm logInForm = (LogInForm) o;

        // перевірка на непорожність
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "application.validation.log-in-form.error-code.email-empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "application.validation.log-in-form.error-code.password-empty");

        // перевірка на коректність
        validate(InputType.EMAIL, logInForm.getEmail(), errors, "email", "application.validation.log-in-form.error-code.email-invalid");
        validate(InputType.PASSWORD, logInForm.getEmail(), errors, "password", "application.validation.log-in-form.error-code.password-invalid");
    }
}

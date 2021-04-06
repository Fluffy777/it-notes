package com.fluffy.spring.validation.validators;

import com.fluffy.spring.validation.forms.SignUpForm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Service
public class SignUpFormValidator extends AbstractFormValidator {
    private final Environment env;

    public SignUpFormValidator(Environment env) {
        this.env = env;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(SignUpForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) o;

        // перевірка на непорожність
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "application.validation.sign-up-form.error-code.first-name-empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "application.validation.sign-up-form.error-code.last-name-empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "application.validation.sign-up-form.error-code.gender-empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "application.validation.sign-up-form.error-code.email-empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "application.validation.sign-up-form.error-code.password-empty");

        // перевірка на коректність
        validate(InputType.NAME, signUpForm.getFirstName(), errors, "firstName", "application.validation.sign-up-form.error-code.first-name-invalid");
        validate(InputType.NAME, signUpForm.getLastName(), errors, "lastName", "application.validation.sign-up-form.error-code.last-name-invalid");
        validate(InputType.GENDER, signUpForm.getGender(), errors, "gender", "application.validation.sign-up-form.error-code.gender-invalid");
        validate(InputType.EMAIL, signUpForm.getEmail(), errors, "email", "application.validation.sign-up-form.error-code.email-invalid");
        validate(InputType.PASSWORD, signUpForm.getEmail(), errors, "password", "application.validation.sign-up-form.error-code.password-invalid");
        validate(InputType.DATE, signUpForm.getBday(), errors, "bday", "application.validation.sign-up-form.error-code.bday-invalid");

        // перевірка на довжину опціональних рядків
        validateNullableStringByLength(signUpForm.getAddress(), Integer.parseInt(env.getProperty("application.validation.sign-up-form.address-max-length")), errors, "address", env.getProperty("application.validation.sign-up-form.error-code.address-too-long"));
    }
}

package com.fluffy.spring.validation.validators;

import com.fluffy.spring.validation.forms.SignUpForm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Service
public class SignUpFormValidator extends LogInFormValidator {
    public SignUpFormValidator(Environment env) {
        super(env);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(SignUpForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        super.validate(o, errors);
        SignUpForm signUpForm = (SignUpForm) o;

        // перевірка на непорожність
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "signUpForm.firstName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "signUpForm.lastName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "signUpForm.gender.empty");

        // перевірка на коректність
        validate(InputType.NAME, signUpForm.getFirstName(), errors, "firstName", "signUpForm.firstName.invalid");
        validate(InputType.NAME, signUpForm.getLastName(), errors, "lastName", "signUpForm.lastName.invalid");
        validate(InputType.GENDER, signUpForm.getGender(), errors, "gender", "signUpForm.gender.invalid");
        String bdayValue = signUpForm.getBday();
        if (bdayValue != null && !bdayValue.isEmpty()) {
            validate(InputType.DATE, signUpForm.getBday(), errors, "bday", "signUpForm.bday.invalid");
        }

        // перевірка на довжину опціональних рядків
        validateNullableStringByLength(signUpForm.getAddress(), Integer.parseInt(env.getProperty("application.signUpForm.address.maxLength")), errors, "address", env.getProperty("signUpForm.address.too-long"));
    }
}

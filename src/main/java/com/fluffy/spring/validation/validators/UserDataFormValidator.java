package com.fluffy.spring.validation.validators;

import com.fluffy.spring.validation.forms.UserDataForm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class UserDataFormValidator extends SignUpFormValidator {
    public UserDataFormValidator(Environment env) {
        super(env);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UserDataFormValidator.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        super.validate(o, errors);



        UserDataForm userDataForm = (UserDataForm) o;
        String newPassword = userDataForm.getNewPassword();
        String newPasswordAgain = userDataForm.getNewPasswordAgain();
        if (newPassword != null && newPasswordAgain != null) {
            // передається новий пароль, отже, другий пароль також повинен передаватися
            // (такий самий)
            if (!newPassword.equals(newPasswordAgain)) {
                errors.rejectValue("newPassword", "");
            }
        }

        // перевірка на довжину опціональних рядків
        validateNullableStringByLength(userDataForm.getDescription(), Integer.parseInt(env.getProperty("application.validation.user-data-form.description-max-length")), errors, "description", "application.validation.user-data-form.error-code.description-too-long");
    }
}

package com.fluffy.spring.validation.validators;

import com.fluffy.spring.controllers.AuthController;
import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import com.fluffy.spring.validation.forms.UserDataForm;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Service
public class UserDataFormValidator extends SignUpFormValidator {
    private final PasswordEncoder passwordEncoder;

    public UserDataFormValidator(UserService userService, PasswordEncoder passwordEncoder, Environment env) {
        super(userService, env);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UserDataForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        // повне перевизначення, оскільки не все вказувати є потрібним
        UserDataForm userDataForm = (UserDataForm) o;

        // перевірка на непорожність
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "signUpForm.firstName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "signUpForm.lastName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "signUpForm.gender.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "logInForm.email.empty");

        // перевірка на коректність
        validate(InputType.NAME, userDataForm.getFirstName(), errors, "firstName", "signUpForm.firstName.invalid");
        validate(InputType.NAME, userDataForm.getLastName(), errors, "lastName", "signUpForm.lastName.invalid");
        validate(InputType.GENDER, userDataForm.getGender(), errors, "gender", "signUpForm.gender.invalid");

        String email = userDataForm.getEmail();
        validate(InputType.EMAIL, email, errors, "email", "logInForm.email.invalid");

        // користувач, який використовує даний email - максимум може бути цим
        // самим користувачем, що оновлює дані про себе, а email залишає без
        // змін)
        User currentUser = userService.findByEmail(AuthController.getCurrentUsername());
        User user = userService.findByEmail(email);
        if ((user != null && currentUser != null) && (user.getId() != currentUser.getId())) {
            // спроба вказати email іншого користувача, а не власний
            errors.rejectValue("email", "signUpForm.email.exists");
        }

        String password = userDataForm.getPassword();
        String newPassword = userDataForm.getNewPassword();
        String newPasswordAgain = userDataForm.getNewPasswordAgain();
        if (password != null && !password.isEmpty()) {
            // користувач вирішив вказати пароль - треба перевірити його
            // відповідність
            if (!passwordEncoder.matches(password, currentUser.getPassword())) {
                // паролі не співпали, а мали - користувач повинен підтвердити
                // зміни введенням старого пароля
                errors.rejectValue("email", "userDataForm.password.bad");
            } else {
                // паролі співпали - користувач підтверджує свої дії
                if (newPassword != null && !newPassword.isEmpty()) {
                    // користувач вказав новий пароль, щоб змінити попередній,
                    if (!newPassword.equals(newPasswordAgain)) {
                        // користувач не зміг підтвердити новий пароль
                        errors.rejectValue("email", "userDataForm.newPasswordAgain.bad");
                    }
                }
            }
        } else {
            // пароль не вказаний - це не є необхідним, якщо користувач не
            // намагається його змінити
            if ((newPassword != null && !newPassword.isEmpty()) || (newPasswordAgain != null && !newPasswordAgain.isEmpty())) {
                // новий пароль не є порожнім - користувач намагається змінити
                // пароль без вказання попереднього, що не можна
                errors.rejectValue("password", "userDataForm.password.confirmationRequired");
            }
        }

        String bdayValue = userDataForm.getBday();
        if (bdayValue != null && !bdayValue.isEmpty()) {
            validate(InputType.DATE, bdayValue, errors, "bday", "signUpForm.bday.invalid");
        }

        // перевірка на довжину опціональних рядків
        validateNullableStringByLength(userDataForm.getAddress(), Integer.parseInt(env.getProperty("application.validation.signUpForm.address.maxLength")), errors, "address", env.getProperty("signUpForm.address.tooLong"));
        validateNullableStringByLength(userDataForm.getAddress(), Integer.parseInt(env.getProperty("application.validation.userDataForm.description.maxLength")), errors, "address", env.getProperty("userDataForm.description.tooLong"));
    }
}

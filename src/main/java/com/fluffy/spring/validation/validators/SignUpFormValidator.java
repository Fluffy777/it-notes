package com.fluffy.spring.validation.validators;

import com.fluffy.spring.services.UserService;
import com.fluffy.spring.validation.forms.SignUpForm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Клас валідатора форми реєстрації.
 * @author Сивоконь Вадим
 */
@Service
public class SignUpFormValidator extends LogInFormValidator {
    /**
     * Сервіс для отримання даних про користувачів.
     */
    protected final UserService userService;

    /**
     * Створює об'єкт (бін) валідатора.
     * @param userService сервіс для отримання даних про користувачів
     * @param env бін для отримання значень властивостей
     */
    public SignUpFormValidator(final UserService userService, final Environment env) {
        super(env);
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return clazz.equals(SignUpForm.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final Object target, final Errors errors) {
        super.validate(target, errors);
        SignUpForm signUpForm = (SignUpForm) target;

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
            validate(InputType.DATE, bdayValue, errors, "bday", "signUpForm.bday.invalid");
        }
        if (userService.findByEmail(signUpForm.getEmail()) != null) {
            errors.rejectValue("email", "signUpForm.email.exists");
        }

        // перевірка на довжину опціональних рядків
        validateNullableStringByLength(signUpForm.getAddress(), Integer.parseInt(env.getProperty("application.validation.signUpForm.address.maxLength")), errors, "address", env.getProperty("signUpForm.address.tooLong"));

        if (errors.hasErrors()) {
            logger.warn("Наявні помилки валідації: " + errors.getAllErrors());
        } else {
            logger.info("Валідація успішно завершена");
        }
    }
}

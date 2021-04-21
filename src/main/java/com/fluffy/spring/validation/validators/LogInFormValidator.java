package com.fluffy.spring.validation.validators;

import com.fluffy.spring.validation.forms.LogInForm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Клас валідатора форми авторизації.
 * @author Сивоконь Вадим
 */
@Service
public class LogInFormValidator extends AbstractFormValidator {
    /**
     * Створює об'єкт (бін) валідатора.
     * @param env бін для отримання значень властивостей
     */
    public LogInFormValidator(final Environment env) {
        super(env);
    }

    /**
     * Перевіряє можливість виконання валідації щодо об'єктів певного класу.
     * @param clazz клас, об'єкти якого будуть валідуватися
     * @return чи можуть об'єкти вказаного класу бути провалідовані
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return clazz.equals(LogInForm.class);
    }

    /**
     * Виконує валідацію даних в об'єкті форми.
     * @param target об'єкт, що валідується
     * @param errors об'єкт, що збиратиме інформацію про помилки
     */
    @Override
    public void validate(final Object target, final Errors errors) {
        LogInForm logInForm = (LogInForm) target;

        // перевірка на непорожність
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "logInForm.email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "logInForm.password.empty");

        // перевірка на коректність
        validate(InputType.EMAIL, logInForm.getEmail(), errors, "email", "logInForm.email.invalid");
        validate(InputType.PASSWORD, logInForm.getPassword(), errors, "password", "logInForm.password.invalid");

        if (errors.hasErrors()) {
            logger.warn("Наявні помилки валідації: " + errors.getAllErrors());
        } else {
            logger.info("Валідація успішно завершена");
        }
    }
}

package com.fluffy.spring.validation.forms;

import com.fluffy.spring.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Базовий клас для форм, пов'язаних із даними користувача.
 * @author Сивоконь Вадим
 */
public abstract class UserForm {
    /**
     * Метод перенесення даних форми на модель користувача - для її часткового
     * заповнення даними.
     * @param newUser модель користувача, що буде заповнюватися
     * @param passwordEncoder бін для шифрування пароля
     */
    protected abstract void translateData(User newUser, PasswordEncoder passwordEncoder);

    /**
     * Створює частково заповнену модель користувача на основі даних, отриманих
     * із форми.
     * @param passwordEncoder бін для шифрування пароля
     * @return частково заповнена модель користувача
     */
    public final User buildUser(final PasswordEncoder passwordEncoder) {
        User user = new User();
        translateData(user, passwordEncoder);
        return user;
    }
}

package com.fluffy.spring.validation.forms;

import com.fluffy.spring.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Клас форми, що приймає дані авторизації.
 * @author Сивоконь Вадим
 */
@Component
public class LogInForm extends UserForm {
    /**
     * Електронна пошта.
     */
    private String email;

    /**
     * Пароль.
     */
    private String password;

    /**
     * Створює порожній об'єкт (бін) форми.
     */
    public LogInForm() { }

    /**
     * Повертає електронну пошту.
     * @return електронна пошта
     */
    public String getEmail() {
        return email;
    }

    /**
     * Встановлює електронну пошту.
     * @param email електронна пошта
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Повертає пароль.
     * @return пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * Встановлює пароль.
     * @param password пароль
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void translateData(final User newUser, final PasswordEncoder passwordEncoder) {
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
    }
}

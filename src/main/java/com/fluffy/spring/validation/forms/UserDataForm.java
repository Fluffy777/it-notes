package com.fluffy.spring.validation.forms;

import com.fluffy.spring.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Клас форми, що приймає дані профілю.
 * @author Сивоконь Вадим
 */
@Component
public class UserDataForm extends SignUpForm {
    /**
     * Опис.
     */
    private String description;

    /**
     * Новий пароль.
     */
    private String newPassword;

    /**
     * Новий пароль (підтвердження).
     */
    private String newPasswordAgain;

    /**
     * Файл зображення профілю.
     */
    private MultipartFile icon;

    /**
     * Створює порожній об'єкт (бін) форми.
     */
    public UserDataForm() {
    }

    /**
     * Повертає опис.
     * @return опис
     */
    public String getDescription() {
        return description;
    }

    /**
     * Встановлює опис.
     * @param description опис
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Повертає новий пароль.
     * @return новий пароль
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Встановлює новий пароль.
     * @param newPassword новий пароль
     */
    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Повертає новий пароль (підтвердження).
     * @return новий пароль (підтвердження)
     */
    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    /**
     * Встановлює новий пароль (підтвердження).
     * @param newPasswordAgain новий пароль (підтвердження)
     */
    public void setNewPasswordAgain(final String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }

    /**
     * Повертає файл зображення профілю.
     * @return файл зображення профілю
     */
    public MultipartFile getIcon() {
        return icon;
    }

    /**
     * Встановлює файл зображення профілю.
     * @param icon файл зображення профілю
     */
    public void setIcon(final MultipartFile icon) {
        this.icon = icon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void translateData(final User newUser, final PasswordEncoder passwordEncoder) {
        super.translateData(newUser, passwordEncoder);
        newUser.setDescription(description);
        newUser.setIcon(icon.getOriginalFilename());
    }
}

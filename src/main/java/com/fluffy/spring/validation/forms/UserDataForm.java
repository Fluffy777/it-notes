package com.fluffy.spring.validation.forms;

import com.fluffy.spring.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDataForm extends SignUpForm {
    private String description;
    private String newPassword;
    private String newPasswordAgain;

    public UserDataForm() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }

    @Override
    protected void translateData(User newUser, PasswordEncoder passwordEncoder) {
        super.translateData(newUser, passwordEncoder);
        newUser.setDescription(description);
    }
}

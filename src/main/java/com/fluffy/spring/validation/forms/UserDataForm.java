package com.fluffy.spring.validation.forms;

import com.fluffy.spring.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

public class UserDataForm extends SignUpForm {
    private String description;
    private String newPassword;
    private String newPasswordAgain;
    private MultipartFile icon;

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

    public MultipartFile getIcon() {
        return icon;
    }

    public void setIcon(MultipartFile icon) {
        this.icon = icon;
    }

    @Override
    protected void translateData(User newUser, PasswordEncoder passwordEncoder) {
        super.translateData(newUser, passwordEncoder);
        newUser.setDescription(description);
        newUser.setIcon(icon.getOriginalFilename());
    }
}

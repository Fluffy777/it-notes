package com.fluffy.spring.validation.forms.users;

import com.fluffy.spring.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LogInForm extends UserForm {
    private String email;
    private String password;

    public LogInForm() { }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected void translateData(User newUser, PasswordEncoder passwordEncoder) {
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
    }
}

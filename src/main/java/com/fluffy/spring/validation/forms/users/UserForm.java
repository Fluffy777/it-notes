package com.fluffy.spring.validation.forms.users;

import com.fluffy.spring.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class UserForm {
    protected abstract void translateData(User newUser, PasswordEncoder passwordEncoder);

    public final User buildUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        translateData(user, passwordEncoder);
        return user;
    }
}

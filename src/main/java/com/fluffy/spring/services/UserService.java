package com.fluffy.spring.services;

import com.fluffy.spring.domain.User;

public interface UserService {
    User findByEmail(String email);

    boolean create(User user);

    boolean update(int userId, User user);
}

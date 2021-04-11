package com.fluffy.spring.services;

import com.fluffy.spring.domain.User;

import java.util.List;

public interface UserService {
    User findById(int userId);

    User findByEmail(String email);

    List<User> findAll();

    User create(User user);

    User update(int userId, User user);

    boolean delete(int userId);
}

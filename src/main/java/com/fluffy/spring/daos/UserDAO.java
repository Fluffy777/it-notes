package com.fluffy.spring.daos;

import com.fluffy.spring.domain.User;

import java.util.List;

public interface UserDAO {
    User getById(int userId);

    List<User> getAll();

    User getByEmail(String email);

    void insert(User user);

    void update(int userId, User role);

    void delete(int userId);
}

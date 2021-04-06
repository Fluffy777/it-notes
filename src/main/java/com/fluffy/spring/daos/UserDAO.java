package com.fluffy.spring.daos;

import com.fluffy.spring.domain.User;

import java.util.List;

public interface UserDAO {
    User getById(int userId);

    List<User> getAll();

    User getByEmail(String email);

    boolean insert(User user);

    boolean update(int userId, User role);

    boolean delete(int userId);
}

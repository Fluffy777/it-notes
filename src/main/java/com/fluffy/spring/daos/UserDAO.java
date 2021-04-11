package com.fluffy.spring.daos;

import com.fluffy.spring.domain.User;

import java.util.List;

public interface UserDAO {
    User getById(int userId);

    List<User> getAll();

    User getByEmail(String email);

    User insert(User user);

    User update(int userId, User user);

    boolean delete(int userId);
}

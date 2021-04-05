package com.fluffy.spring.daos;

import com.fluffy.spring.domain.User;

import java.util.List;

public interface UserDAO {
    User get(int primaryKey);

    List<User> getAll();

    void insert(User user);

    void update(int primaryKey, User role);

    void delete(int primaryKey);
}

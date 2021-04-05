package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Role;

import java.util.List;

public interface RoleDAO {
    Role get(int primaryKey);

    List<Role> getAll();

    void insert(Role role);

    void update(int primaryKey, Role role);

    void delete(int primaryKey);
}

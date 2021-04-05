package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Role;

import java.util.List;

public interface RoleDAO {
    Role getById(int roleId);

    List<Role> getAll();
}

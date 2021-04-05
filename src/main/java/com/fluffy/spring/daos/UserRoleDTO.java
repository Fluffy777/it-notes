package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Role;

import java.util.Set;

public interface UserRoleDTO {
    Set<Role> getAllByUserId(int userId);
}

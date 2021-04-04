package com.fluffy.spring.services;

import com.fluffy.spring.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}

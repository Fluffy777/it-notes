package com.fluffy.spring.services.impls;

import com.fluffy.spring.daos.UserDAO;
import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> findByEmail(String email) {

    }
}

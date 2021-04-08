package com.fluffy.spring.services.impls;

import com.fluffy.spring.daos.UserDAO;
import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    @Override
    public boolean create(User user) {
        // під час створення вважаємо, що користувач не є заблокованим, а часом
        // регістрації - цей момент
        user.setEnabled(true);
        user.setRday(new java.sql.Date(new Date().getTime()));
        return userDAO.insert(user);
    }

    @Override
    public boolean update(int userId, User user) {
        return userDAO.update(userId, user);
    }
}

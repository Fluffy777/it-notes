package com.fluffy.spring.services.impls;

import com.fluffy.spring.daos.UserDAO;
import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(int userId) {
        return userDAO.getById(userId);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    @Override
    public User create(User user) {
        // під час створення вважаємо, що користувач не є заблокованим, а часом
        // регістрації - цей момент
        user.setEnabled(true);
        user.setRday(new java.sql.Date(new Date().getTime()));
        return userDAO.insert(user);
    }

    @Override
    public User update(int userId, User user) {
        return userDAO.update(userId, user);
    }

    @Override
    public boolean delete(int userId) {
        return userDAO.delete(userId);
    }

    @Override
    public List<User> findAll() {
        return userDAO.getAll();
    }
}

package com.fluffy.spring.services.impls;

import com.fluffy.spring.daos.UserDAO;
import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Сервіс для отримання даних про користувачів.
 * @author Сивоконь Вадим
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * Бін для отримання інформації про користувачів.
     */
    private final UserDAO userDAO;

    /**
     * Створює об'єкт сервісу.
     * @param userDAO бін для отримання інформації про користувачів
     */
    public UserServiceImpl(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(final int userId) {
        return userDAO.getById(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByEmail(final String email) {
        return userDAO.getByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(final User user) {
        // під час створення вважаємо, що користувач не є заблокованим, а часом
        // регістрації - цей момент
        user.setEnabled(true);
        user.setRday(new java.sql.Date(new Date().getTime()));
        return userDAO.insert(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User update(final int userId, final User user) {
        return userDAO.update(userId, user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final int userId) {
        return userDAO.delete(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        return userDAO.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAllWithRoleName(final String roleName) {
        return userDAO.getAllWithRoleName(roleName);
    }
}

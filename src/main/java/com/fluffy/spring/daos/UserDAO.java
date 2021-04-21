package com.fluffy.spring.daos;

import com.fluffy.spring.domain.User;

import java.util.List;

/**
 * Інтерфейс для отримання інформації про користувачів.
 * @author Сивоконь Вадим
 */
public interface UserDAO {
    /**
     * Повертає користувача за ID.
     * @param userId ID користувача
     * @return користувач
     */
    User getById(int userId);

    /**
     * Повертає користувача за електронною поштою.
     * @param email електронна пошта
     * @return користувач
     */
    User getByEmail(String email);

    /**
     * Повертає всіх користувачів.
     * @return усі користувачі
     */
    List<User> getAll();

    /**
     * Повертає користувачів, що мають роль із назвою.
     * @param roleName назва ролі
     * @return усі користувачі, що мають роль із назвою
     */
    List<User> getAllWithRoleName(String roleName);

    /**
     * Зберігає модель користувача у базі даних.
     * @param user модель користувача
     * @return модель користувача, що збережена
     */
    User insert(User user);

    /**
     * Оновлює модель користувача у базі даних.
     * @param userId ID моделі користувача
     * @param user модель користувача
     * @return модель користувача, що була оновлена
     */
    User update(int userId, User user);

    /**
     * Видаляє модель користувача із бази даних.
     * @param userId ID моделі користувача
     * @return чи була модель користувача видалена
     */
    boolean delete(int userId);
}

package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Role;
import com.fluffy.spring.domain.UserRole;

import java.util.Set;

/**
 * Інтерфейс для отримання інформації про ролі користувачів.
 * @author Сивоконь Вадим
 */
public interface UserRoleDAO {
    /**
     * Повертає всі ролі користувача для користувача із ID.
     * @param userId ID користувача
     * @return усі ролі користувача для користувача із ID
     */
    Set<Role> getAllByUserId(int userId);

    /**
     * Зберігає модель ролі користувача у базі даних.
     * @param userRole модель ролі користувача
     * @return модель ролі користувача, що була збережена
     */
    UserRole insert(UserRole userRole);
}

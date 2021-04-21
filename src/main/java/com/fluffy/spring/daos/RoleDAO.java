package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Role;

import java.util.List;

/**
 * Інтерфейс для отримання інформації про ролі.
 * @author Сивоконь Вадим
 */
public interface RoleDAO {
    /**
     * Повертає роль за ID.
     * @param roleId ID моделі ролі
     * @return роль
     */
    Role getById(int roleId);

    /**
     * Повертає роль за назвою.
     * @param name назва ролі
     * @return роль
     */
    Role getByName(String name);

    /**
     * Повертає всі ролі.
     * @return усі ролі
     */
    List<Role> getAll();
}

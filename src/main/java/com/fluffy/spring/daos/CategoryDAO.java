package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Category;

import java.util.List;

/**
 * Інтерфейс для отримання інформації про категорії.
 * @author Сивоконь Вадим
 */
public interface CategoryDAO {
    /**
     * Повертає категорію за ID.
     * @param categoryId ID категорії
     * @return категорія
     */
    Category getById(int categoryId);

    /**
     * Повертає категорію за назвою.
     * @param name назва категорії
     * @return категорія
     */
    Category getByName(String name);

    /**
     * Повертає всі категорії.
     * @return усі категорії
     */
    List<Category> getAll();

    /**
     * Зберігає модель категорії в базі даних.
     * @param category модель категорії
     * @return модель категорії, що збережена
     */
    Category insert(Category category);

    /**
     * Оновлює модель категорії в базі даних.
     * @param categoryId ID моделі категорії
     * @param category модель категорії
     * @return модель категорії, що була оновлена
     */
    Category update(int categoryId, Category category);

    /**
     * Видаляє модель категорії із бази даних.
     * @param categoryId ID моделі категорії
     * @return чи була модель категорії видалена
     */
    boolean delete(int categoryId);
}

package com.fluffy.spring.services;

import com.fluffy.spring.domain.Category;

import java.util.List;

/**
 * Інтерфейс сервісу для отримання даних про категорії.
 * @author Сивоконь Вадим
 */
public interface CategoryService {
    /**
     * Повертає категорію за ID.
     * @param categoryId ID категорії
     * @return категорія
     */
    Category findById(int categoryId);

    /**
     * Повертає категорію за назвою.
     * @param name назва категорії
     * @return категорія
     */
    Category findByName(String name);

    /**
     * Повертає всі категорії.
     * @return усі категорії
     */
    List<Category> findAll();

    /**
     * Зберігає модель категорії в базі даних.
     * @param category модель категорії
     * @return модель категорії, що збережена
     */
    Category create(Category category);

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

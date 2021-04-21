package com.fluffy.spring.services.impls;

import com.fluffy.spring.daos.CategoryDAO;
import com.fluffy.spring.domain.Category;
import com.fluffy.spring.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервіс для отримання даних про категорії.
 * @author Сивоконь Вадим
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    /**
     * Бін для отримання інформації про категорії.
     */
    private final CategoryDAO categoryDAO;

    /**
     * Створює об'єкт сервісу.
     * @param categoryDAO бін для отримання інформації про категорії
     */
    public CategoryServiceImpl(final CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category findByName(final String name) {
        return categoryDAO.getByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category create(final Category category) {
        return categoryDAO.insert(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category update(final int categoryId, final Category category) {
        return categoryDAO.update(categoryId, category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final int categoryId) {
        return categoryDAO.delete(categoryId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findAll() {
        return categoryDAO.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category findById(final int categoryId) {
        return categoryDAO.getById(categoryId);
    }
}

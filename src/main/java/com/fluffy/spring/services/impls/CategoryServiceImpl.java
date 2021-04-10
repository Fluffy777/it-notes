package com.fluffy.spring.services.impls;

import com.fluffy.spring.daos.CategoryDAO;
import com.fluffy.spring.domain.Category;
import com.fluffy.spring.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public Category findByName(String name) {
        return categoryDAO.getByName(name);
    }

    @Override
    public Category create(Category category) {
        return categoryDAO.insert(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryDAO.getAll();
    }

    @Override
    public Category findById(int categoryId) {
        return categoryDAO.getById(categoryId);
    }
}

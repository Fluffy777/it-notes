package com.fluffy.spring.services;

import com.fluffy.spring.domain.Category;

import java.util.List;

public interface CategoryService {
    Category findByName(String name);

    Category create(Category category);

    List<Category> findAll();

    Category findById(int categoryId);
}

package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Category;

import java.util.List;

public interface CategoryDAO {
    Category getById(int categoryId);

    Category getByName(String name);

    List<Category> getAll();

    Category insert(Category category);
}

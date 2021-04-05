package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Category;

public interface CategoryDAO {
    Category getById(int categoryId);
}

package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Article;
import com.fluffy.spring.domain.User;

import java.util.List;

public interface ArticleDAO {
    Article getById(int articleId);

    List<Article> getAll();

    List<Article> getAllByCategoryId(int categoryId);

    boolean insert(Article article);

    List<Article> getMostPopular(int topSize);
}

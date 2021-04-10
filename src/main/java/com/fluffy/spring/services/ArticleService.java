package com.fluffy.spring.services;

import com.fluffy.spring.domain.Article;

import java.util.List;

public interface ArticleService {
    boolean create(Article article);

    List<Article> findAll();

    List<Article> findMostPopular(int topSize);
}

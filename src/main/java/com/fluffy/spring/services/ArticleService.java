package com.fluffy.spring.services;

import com.fluffy.spring.domain.Article;

import java.util.List;

public interface ArticleService {
    Article create(Article article);

    Article update(int articleId, Article article);

    boolean delete(int articleId);

    Article findById(int articleId);

    List<Article> findAll();

    List<Article> findMostPopular(int topSize);
}

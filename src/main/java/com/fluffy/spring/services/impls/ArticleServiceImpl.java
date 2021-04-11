package com.fluffy.spring.services.impls;

import com.fluffy.spring.daos.ArticleDAO;
import com.fluffy.spring.domain.Article;
import com.fluffy.spring.services.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleDAO articleDAO;

    public ArticleServiceImpl(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public Article create(Article article) {
        return articleDAO.insert(article);
    }

    @Override
    public Article update(int articleId, Article article) {
        return articleDAO.update(articleId, article);
    }

    @Override
    public boolean delete(int articleId) {
        return articleDAO.delete(articleId);
    }

    @Override
    public Article findById(int articleId) {
        return articleDAO.getById(articleId);
    }

    @Override
    public List<Article> findAll() {
        return articleDAO.getAll();
    }

    @Override
    public List<Article> findMostPopular(int topSize) {
        return articleDAO.getMostPopular(topSize);
    }
}

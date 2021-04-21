package com.fluffy.spring.services.impls;

import com.fluffy.spring.daos.ArticleDAO;
import com.fluffy.spring.domain.Article;
import com.fluffy.spring.domain.FilteringArticle;
import com.fluffy.spring.services.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервіс для отримання даних про статті.
 * @author Сивоконь Вадим
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    /**
     * Бін для отримання інформації про статті.
     */
    private final ArticleDAO articleDAO;

    /**
     * Створює об'єкт сервісу.
     * @param articleDAO бін для отримання інформації про статті
     */
    public ArticleServiceImpl(final ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Article create(final Article article) {
        return articleDAO.insert(article);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Article update(final int articleId, final Article article) {
        return articleDAO.update(articleId, article);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final int articleId) {
        return articleDAO.delete(articleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Article findById(final int articleId) {
        return articleDAO.getById(articleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Article> findAll(final FilteringArticle filteringArticle) {
        return articleDAO.getAll(filteringArticle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int findAllAmount(final FilteringArticle filteringArticle) {
        return articleDAO.getAllAmount(filteringArticle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Article> findMostPopular(final int topSize) {
        return articleDAO.getMostPopular(topSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Article> findSame(final int articleId, final int maxCount) {
        return articleDAO.getSame(articleId, maxCount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int findLastCommentLocalId(final int articleId) {
        return articleDAO.getLastCommentLocalId(articleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean increaseViews(final int articleId) {
        return articleDAO.increaseViews(articleId);
    }
}

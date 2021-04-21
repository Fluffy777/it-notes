package com.fluffy.spring.services;

import com.fluffy.spring.domain.Article;
import com.fluffy.spring.domain.FilteringArticle;

import java.util.List;

/**
 * Інтерфейс сервісу для отримання даних про статті.
 * @author Сивоконь Вадим
 */
public interface ArticleService {
    /**
     * Повертає статтю за ID.
     * @param articleId ID статті
     * @return стаття
     */
    Article findById(int articleId);

    /**
     * Повертає найбільш популярні статті.
     * @param topSize розмір топ-рейтингу
     * @return найбільш популярні статті
     */
    List<Article> findMostPopular(int topSize);

    /**
     * Повертає схожі за категорією статті.
     * @param articleId ID статті, для якої треба шукати схожі
     * @param maxCount максимальна кількість схожих статей
     * @return схожі статті
     */
    List<Article> findSame(int articleId, int maxCount);

    /**
     * Повертає статті, що відповідають встановленому фільтру.
     * @param filteringArticle фільтр для статей
     * @return статті, що відповідають фільтру
     */
    List<Article> findAll(FilteringArticle filteringArticle);

    /**
     * Повертає кількість статей, що відповідають встановленому фільтру.
     * @param filteringArticle фільтр для статей
     * @return статті, що відповідають фільтру
     */
    int findAllAmount(FilteringArticle filteringArticle);

    /**
     * Повертає останній використаний локальний ID коментаря за статею під ID.
     * @param articleId ID статті
     * @return останній використаний локальний ID
     */
    int findLastCommentLocalId(int articleId);

    /**
     * Зберігає модель статті в базі даних.
     * @param article модель статті
     * @return модель статті, що збережена
     */
    Article create(Article article);

    /**
     * Оновлює модель статті в базі даних.
     * @param articleId ID моделі статті
     * @param article модель статті
     * @return модель статті, що була оновлена
     */
    Article update(int articleId, Article article);

    /**
     * Видаляє модель статті із бази даних.
     * @param articleId ID моделі статті
     * @return чи була модель статті видалена
     */
    boolean delete(int articleId);

    /**
     * Збільшує кількість переглядів статті (її часткове оновлення).
     * @param articleId ID моделі статті
     * @return чи вдалося оновити кількість переглядів
     */
    boolean increaseViews(int articleId);
}

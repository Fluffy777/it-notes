package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Article;
import com.fluffy.spring.domain.FilteringArticle;

import java.util.List;

/**
 * Інтерфейс для отримання інформації про статті.
 * @author Сивоконь Вадим
 */
public interface ArticleDAO {
    /**
     * Повертає статтю за ID.
     * @param articleId ID статті
     * @return стаття
     */
    Article getById(int articleId);

    /**
     * Повертає найбільш популярні статті.
     * @param topSize розмір топ-рейтингу
     * @return найбільш популярні статті
     */
    List<Article> getMostPopular(int topSize);

    /**
     * Повертає схожі за категорією статті.
     * @param articleId ID статті, для якої треба шукати схожі
     * @param maxCount максимальна кількість схожих статей
     * @return схожі статті
     */
    List<Article> getSame(int articleId, int maxCount);

    /**
     * Повертає статті, що відповідають встановленому фільтру.
     * @param filteringArticle фільтр для статей
     * @return статті, що відповідають фільтру
     */
    List<Article> getAll(FilteringArticle filteringArticle);

    /**
     * Повертає кількість статей, що належать до категорії під ID.
     * @param categoryId ID категорії
     * @return кількість статей, що належать до категорії під ID
     */
    int getAmountOfArticlesByCategoryId(int categoryId);

    /**
     * Повертає кількість статей, що відповідають встановленому фільтру.
     * @param filteringArticle фільтр для статей
     * @return статті, що відповідають фільтру
     */
    int getAllAmount(FilteringArticle filteringArticle);

    /**
     * Повертає останній використаний локальний ID коментаря за статею під ID.
     * @param articleId ID статті
     * @return останній використаний локальний ID
     */
    int getLastCommentLocalId(int articleId);

    /**
     * Зберігає модель статті в базі даних.
     * @param article модель статті
     * @return модель статті, що збережена
     */
    Article insert(Article article);

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

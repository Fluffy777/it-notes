package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Comment;

import java.util.List;

/**
 * Інтерфейс для отримання інформації про коментарі.
 * @author Сивоконь Вадим
 */
public interface CommentDAO {
    /**
     * Повертає коментар за ID.
     * @param commentId ID коментаря
     * @return коментар
     */
    Comment getById(int commentId);

    /**
     * Повертає всі коментарі до статті.
     * @param articleId ID статті
     * @return коментарі до статті
     */
    List<Comment> getAllByArticleId(int articleId);

    /**
     * Зберігає модель коментаря у базі даних.
     * @param comment модель коментаря
     * @return модель коментаря, що збережена
     */
    Comment insert(Comment comment);

    /**
     * Оновлює модель коментаря у базі даних.
     * @param commentId ID моделі коментаря
     * @param comment модель коментаря
     * @return модель коментаря, що була оновлена
     */
    Comment update(int commentId, Comment comment);

    /**
     * Видаляє модель коментаря із бази даних.
     * @param commentId ID моделі коментаря
     * @return чи була модель коментаря видалена
     */
    boolean delete(int commentId);
}

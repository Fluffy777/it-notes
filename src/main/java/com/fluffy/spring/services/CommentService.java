package com.fluffy.spring.services;

import com.fluffy.spring.domain.Comment;

import java.util.List;

/**
 * Інтерфейс сервісу для отримання даних про коментарі.
 * @author Сивоконь Вадим
 */
public interface CommentService {
    /**
     * Повертає коментар за ID.
     * @param commentId ID коментаря
     * @return коментар
     */
    Comment findById(int commentId);

    /**
     * Повертає всі коментарі до статті.
     * @param articleId ID статті
     * @return коментарі до статті
     */
    List<Comment> findAllByArticleId(int articleId);

    /**
     * Зберігає модель коментаря у базі даних.
     * @param comment модель коментаря
     * @return модель коментаря, що збережена
     */
    Comment create(Comment comment);

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

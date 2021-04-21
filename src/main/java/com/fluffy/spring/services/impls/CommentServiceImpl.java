package com.fluffy.spring.services.impls;

import com.fluffy.spring.daos.CommentDAO;
import com.fluffy.spring.domain.Comment;
import com.fluffy.spring.services.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервіс для отримання даних про коментарі.
 * @author Сивоконь Вадим
 */
@Service
public class CommentServiceImpl implements CommentService {
    /**
     * Бін для отримання інформації про коментарі.
     */
    private final CommentDAO commentDAO;

    /**
     * Створює об'єкт сервісу.
     * @param commentDAO бін для отримання інформації про коментарі
     */
    public CommentServiceImpl(final CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comment findById(final int commentId) {
        return commentDAO.getById(commentId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Comment> findAllByArticleId(final int articleId) {
        return commentDAO.getAllByArticleId(articleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comment create(final Comment comment) {
        return commentDAO.insert(comment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comment update(final int commentId, final Comment comment) {
        return commentDAO.update(commentId, comment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final int commentId) {
        return commentDAO.delete(commentId);
    }
}

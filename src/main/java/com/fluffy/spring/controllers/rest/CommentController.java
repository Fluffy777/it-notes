package com.fluffy.spring.controllers.rest;

import com.fluffy.spring.domain.Comment;
import com.fluffy.spring.services.ArticleService;
import com.fluffy.spring.services.CommentService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Клас REST-контролера, що надає можливість маніпулювати коментарями.
 * @author Сивоконь Вадим
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(CommentController.class);

    /**
     * Сервіс для отримання даних про коментарі.
     */
    private final CommentService commentService;

    /**
     * Сервіс для отримання даних про статті.
     */
    private final ArticleService articleService;

    /**
     * Створює об'єкт контролера.
     * @param commentService сервіс для отримання даних про коментарі
     * @param articleService сервіс для отримання даних про статті
     */
    public CommentController(final CommentService commentService,
                             final ArticleService articleService) {
        this.commentService = commentService;
        this.articleService = articleService;
    }

    /**
     * Повертає коментар за його ID.
     * @param id ID коментаря
     * @return коментар
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable final int id) {
        Comment comment = commentService.findById(id);
        if (comment != null) {
            logger.info("Надані дані про коментар із id = " + id);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
        logger.warn("Не вдалося знайти дані про коментар із id = " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Повертає коментарі під статтею (за її ID).
     * @param articleId ID статті
     * @return коментарі під статтею
     */
    @GetMapping("/by-article")
    public ResponseEntity<?> getAllCommentsByArticleId(@RequestParam final int articleId) {
        logger.info("Надані дані про коментарі до статті із articleId = " + articleId);
        return new ResponseEntity<>(commentService.findAllByArticleId(articleId), HttpStatus.OK);
    }

    /**
     * Створює коментар, що відповідає переданому об'єкту. Локальний ID, дата
     * модифікації оновлюються на боці сервера із метою узгодження.
     * @param comment модель коментаря (заповнена частково, не представлена в
     *                базі даних)
     * @return модель коментаря (заповнена повністю, представлена в базі даних)
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createComment(@RequestBody final Comment comment) {
        comment.setLocalId(articleService.findLastCommentLocalId(comment.getArticle().getId()) + 1);
        comment.setModificationDate(new java.sql.Date(new Date().getTime()));
        Comment createdComment = commentService.create(comment);
        logger.info("Коментар із id = " + comment.getId() + " (localId = " + comment.getLocalId() + ") був створений");
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    /**
     * Оновлює коментар, що відповідає переданому об'єкту. Дата модифікації
     * оновлюється на боці сервера.
     * @param comment модель коментаря (заповнена частково, представлена в базі
     *                даних)
     * @return оновлена модель коментаря (заповнена повністю, представлена в
     *         базі даних)
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateComment(@RequestBody final Comment comment) {
        comment.setModificationDate(new java.sql.Date(new Date().getTime()));
        Comment updatedComment = commentService.update(comment.getId(), comment);
        logger.info("Коментар із id = " + comment.getId() + " був оновлений");
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    /**
     * Видаляє коментар за вказаним ID.
     * @param id ID коментаря
     * @return повідомлення-відповідь щодо успіху видалення коментаря
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable final int id) {
        if (commentService.delete(id)) {
            logger.info("Коментар, у якого id = " + id + " був видалений");
            return new ResponseEntity<>("Коментар, у якого comment_id = " + id + " був видалений", HttpStatus.OK);
        }
        logger.warn("Не вдалося видалити коментар із id = " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

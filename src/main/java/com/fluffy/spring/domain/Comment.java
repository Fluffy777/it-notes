package com.fluffy.spring.domain;

import java.sql.Date;

/**
 * Клас моделі коментаря.
 * @author Сивоконь Вадим
 */
public class Comment {
    /**
     * ID коментаря.
     */
    private int id;

    /**
     * Стаття, до якої належить коментар.
     */
    private Article article;

    /**
     * Локальний ID коментаря.
     */
    private int localId;

    /**
     * ID батьківського коментаря.
     */
    private Integer parentId;

    /**
     * Автор.
     */
    private User user;

    /**
     * Контент.
     */
    private String content;

    /**
     * Дата оновлення.
     */
    private Date modificationDate;

    /**
     * Створює порожню модель коментаря.
     */
    public Comment() { }

    /**
     * Повертає ID коментаря.
     * @return ID коментаря
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ID коментаря.
     * @param id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Повертає статтю, до якої належить коментар.
     * @return стаття, до якої належить коментар
     */
    public Article getArticle() {
        return article;
    }

    /**
     * Встановлює статтю, до якої належить коментар.
     * @param article стаття, до якої належить коментар
     */
    public void setArticle(final Article article) {
        this.article = article;
    }

    /**
     * Повертає локальний ID коментаря.
     * @return локальний ID коментаря
     */
    public int getLocalId() {
        return localId;
    }

    /**
     * Встановлює локальний ID коментаря.
     * @param localId локальний ID коментаря
     */
    public void setLocalId(final int localId) {
        this.localId = localId;
    }

    /**
     * Повертає ID батьківського коментаря.
     * @return ID батьківського коментаря
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * Встановлює ID батьківського коментаря.
     * @param parentId ID батьківського коментаря
     */
    public void setParentId(final Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * Повертає автора.
     * @return автор
     */
    public User getUser() {
        return user;
    }

    /**
     * Встановлює автора.
     * @param user автор
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * Повертає контент.
     * @return контент
     */
    public String getContent() {
        return content;
    }

    /**
     * Встановлює контент.
     * @param content контент
     */
    public void setContent(final String content) {
        this.content = content;
    }

    /**
     * Повертає дату оновлення.
     * @return дата оновлення
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * Встановлює дату оновлення.
     * @param modificationDate дата оновлення
     */
    public void setModificationDate(final Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}

package com.fluffy.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

/**
 * Клас моделі статті.
 * @author Сивоконь Вадим
 */
public class Article {
    /**
     * ID статті.
     */
    private int id;

    /**
     * Категорія.
     */
    private Category category;

    /**
     * Автор.
     */
    private User user;

    /**
     * Назва.
     */
    private String name;

    /**
     * Контент.
     */
    private String content;

    /**
     * Дата оновлення.
     */
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date modificationDate;

    /**
     * Кількість переглядів.
     */
    private Integer views;

    /**
     * Створює порожню модель статті.
     */
    public Article() { }

    /**
     * Повертає ID статті.
     * @return ID статті.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ID статті.
     * @param id ID статті.
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Повертає категорію.
     * @return категорія
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Встановлює категорію.
     * @param category категорія
     */
    public void setCategory(final Category category) {
        this.category = category;
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
     * Повертає назву.
     * @return назва
     */
    public String getName() {
        return name;
    }

    /**
     * Встановлює назву.
     * @param name назва
     */
    public void setName(final String name) {
        this.name = name;
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

    /**
     * Повертає кількість переглядів.
     * @return кількість переглядів
     */
    public Integer getViews() {
        return views;
    }

    /**
     * Встановлює кількість переглядів.
     * @param views кількість переглядів
     */
    public void setViews(final Integer views) {
        this.views = views;
    }
}

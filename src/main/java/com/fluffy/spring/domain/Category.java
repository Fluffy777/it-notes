package com.fluffy.spring.domain;

/**
 * Клас моделі категорії.
 * @author Сивоконь Вадим
 */
public class Category {
    /**
     * ID категорії.
     */
    private int id;

    /**
     * Назва.
     */
    private String name;

    /**
     * Кількість статтей.
     */
    private int amountOfArticles;

    /**
     * Створює порожню модель категорії.
     */
    public Category() { }

    /**
     * Повертає ID категорії.
     * @return ID категорії
     */
    public int getId() {
        return id;
    }

    /** Встановлює ID категорії.
     * @param id ID категорії
     */
    public void setId(final int id) {
        this.id = id;
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
     * Повертає кількість статей.
     * @return кількість статей
     */
    public int getAmountOfArticles() {
        return amountOfArticles;
    }

    /**
     * Встановлює кількість статей.
     * @param amountOfArticles кількість статей
     */
    public void setAmountOfArticles(final int amountOfArticles) {
        this.amountOfArticles = amountOfArticles;
    }
}

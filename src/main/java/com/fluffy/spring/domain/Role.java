package com.fluffy.spring.domain;

/**
 * Клас моделі ролі.
 * @author Сивоконь Вадим
 */
public class Role {
    /**
     * ID ролі.
     */
    private int id;

    /**
     * Назва.
     */
    private String name;

    /**
     * Створює порожню модель ролі.
     */
    public Role() { }

    /**
     * Повертає ID ролі.
     * @return ID ролі
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ID ролі.
     * @param id ID ролі
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
}

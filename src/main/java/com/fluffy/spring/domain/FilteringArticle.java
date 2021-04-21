package com.fluffy.spring.domain;

/**
 * Клас моделі фільтруючої статті.
 * @author Сивоконь Вадим
 */
public class FilteringArticle extends Article {
    /**
     * Колонка для сортування.
     */
    private String orderBy;

    /**
     * Напрямок для сортування.
     */
    private String orderDirection;

    /**
     * Операція порівняння для категорії.
     */
    private String categoryOperation;

    /**
     * Операція порівняння для користувачів.
     */
    private String userOperation;

    /**
     * Операція порівняння для назв.
     */
    private String nameOperation;

    /**
     * Операція порівняння для контенту.
     */
    private String contentOperation;

    /**
     * Операція порівняння для дат оновлення.
     */
    private String modificationDateOperation;

    /**
     * Операція порівняння для кількостей переглядів.
     */
    private String viewsOperation;

    /**
     * Номер сторінки.
     */
    private Integer page;

    /**
     * Розмір сторінки.
     */
    private Integer pageSize;

    /**
     * Створює порожню модель фільтруючої статті.
     */
    public FilteringArticle() {
    }

    /**
     * Повертає колонку для сортування.
     * @return колонка для сортування
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Встановлює колонку для сортування.
     * @param orderBy колонка для сортування
     */
    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * Повертає напрямок сортування.
     * @return напрямок сортування
     */
    public String getOrderDirection() {
        return orderDirection;
    }

    /**
     * Встановлює напрямок сортування.
     * @param orderDirection напрямок сортування
     */
    public void setOrderDirection(final String orderDirection) {
        this.orderDirection = orderDirection;
    }

    /**
     * Повертає операцію порівняння для категорій.
     * @return операція порівняння для категорій
     */
    public String getCategoryOperation() {
        return categoryOperation;
    }

    /**
     * Встановлює операцію порівняння для категорій.
     * @param categoryOperation операція порівняння для категорій
     */
    public void setCategoryOperation(final String categoryOperation) {
        this.categoryOperation = categoryOperation;
    }

    /**
     * Повертає операцію порівняння для користувачів.
     * @return операція порівняння для користувачів
     */
    public String getUserOperation() {
        return userOperation;
    }

    /**
     * Встановлює операцію порівняння для користувачів.
     * @param userOperation операція порівняння для користувачів
     */
    public void setUserOperation(final String userOperation) {
        this.userOperation = userOperation;
    }

    /**
     * Повертає операцію порівняння для назв.
     * @return операція порівняння для назв
     */
    public String getNameOperation() {
        return nameOperation;
    }

    /**
     * Встановлює операцію порівняння для назв.
     * @param nameOperation операція порівняння для назв
     */
    public void setNameOperation(final String nameOperation) {
        this.nameOperation = nameOperation;
    }

    /**
     * Повертає операцію порівняння для контенту.
     * @return операція порівняння для контенту
     */
    public String getContentOperation() {
        return contentOperation;
    }

    /**
     * Встановлює операцію порівняння для контенту.
     * @param contentOperation операція порівняння для контенту
     */
    public void setContentOperation(final String contentOperation) {
        this.contentOperation = contentOperation;
    }

    /**
     * Повертає операцію порівняння для дат оновлення.
     * @return операція порівняння для дат оновлення
     */
    public String getModificationDateOperation() {
        return modificationDateOperation;
    }

    /**
     * Встановлює операцію порівняння для дат оновлення.
     * @param modificationDateOperation операція порівняння для дат оновлення
     */
    public void setModificationDateOperation(final String modificationDateOperation) {
        this.modificationDateOperation = modificationDateOperation;
    }

    /**
     * Повертає операцію порівняння для кількостей переглядів.
     * @return операція порівняння для кількостей переглядів
     */
    public String getViewsOperation() {
        return viewsOperation;
    }

    /**
     * Встановлює операцію порівняння для кількостей переглядів.
     * @param viewsOperation операція порівняння для кількостей переглядів
     */
    public void setViewsOperation(final String viewsOperation) {
        this.viewsOperation = viewsOperation;
    }

    /**
     * Повертає номер сторінки.
     * @return номер сторінки
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Встановлює номер сторінки.
     * @param page номер сторінки
     */
    public void setPage(final Integer page) {
        this.page = page;
    }

    /**
     * Повертає розмір сторінки.
     * @return розмір сторінки
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Встановлює розмір сторінки.
     * @param pageSize розмір сторінки
     */
    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }
}

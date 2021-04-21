package com.fluffy.spring.domain;

/**
 * Клас моделі ролі користувача.
 * @author Сивоконь Вадим
 */
public class UserRole {
    /**
     * Користувач.
     */
    private User user;

    /**
     * Роль.
     */
    private Role role;

    /**
     * Створює порожню модель ролі користувача.
     */
    public UserRole() { }

    /**
     * Повертає користувача.
     * @return користувач
     */
    public User getUser() {
        return user;
    }

    /**
     * Встановлює користувача.
     * @param user користувач
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * Повертає роль.
     * @return роль
     */
    public Role getRole() {
        return role;
    }

    /**
     * Встановлює роль.
     * @param role роль
     */
    public void setRole(final Role role) {
        this.role = role;
    }
}

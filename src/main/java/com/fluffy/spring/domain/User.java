package com.fluffy.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.Set;

public class User {
    /**
     * ID користувача.
     */
    private int id;

    /**
     * Список ролей.
     */
    private Set<Role> roles;

    /**
     * Доступність.
     */
    private boolean enabled;

    /**
     * Ім'я.
     */
    private String firstName;

    /**
     * Прізвище.
     */
    private String lastName;

    /**
     * Гендер.
     */
    private Gender gender;

    /**
     * Електронна пошта.
     */
    private String email;

    /**
     * Пароль.
     */
    private String password;

    /**
     * Дата реєстрації.
     */
    private Date rday;

    /**
     * Дата народження.
     */
    private Date bday;

    /**
     * Опис.
     */
    private String description;

    /**
     * Адреса.
     */
    private String address;

    /**
     * Назва зображення профілю.
     */
    private String icon;

    /**
     * Можливі гендери для обрання користувачем.
     */
    public enum Gender {
        /**
         * Чоловік.
         */
        MALE,

        /**
         * Жінка.
         */
        FEMALE
    }

    /**
     * Створює порожню модель користувача.
     */
    public User() { }

    /**
     * Повертає ID користувача.
     * @return ID користувача
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ID користувача.
     * @param id ID користувача
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Повертає список ролей.
     * @return список ролей
     */
    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Встановлює список ролей.
     * @param roles список ролей
     */
    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Повертає доступність.
     * @return доступність
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Встановлює доступність.
     * @param enabled доступність
     */
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Повертає ім'я.
     * @return ім'я
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Встановлює ім'я.
     * @param firstName ім'я
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Повертає прізвище.
     * @return прізвище
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Встановлює прізвище.
     * @param lastName прізвище
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Повертає гендер.
     * @return гендер
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Встановлює гендер.
     * @param gender гендер
     */
    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    /**
     * Повертає електронну пошту.
     * @return електронна пошта
     */
    @JsonIgnore
    public String getEmail() {
        return email;
    }

    /**
     * Встановлює електронну пошту.
     * @param email електронна пошта
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Повертає пароль.
     * @return пароль
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * Встановлює пароль.
     * @param password пароль
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Повертає дату реєстрації.
     * @return дата реєстрації
     */
    public Date getRday() {
        return rday;
    }

    /**
     * Встановлює дату реєстрації.
     * @param rday дата реєстрації
     */
    public void setRday(final Date rday) {
        this.rday = rday;
    }

    /**
     * Повертає дату народження.
     * @return дата народження
     */
    public Date getBday() {
        return bday;
    }

    /**
     * Встановлює дату народження.
     * @param bday дата народження
     */
    public void setBday(final Date bday) {
        this.bday = bday;
    }

    /**
     * Повертає опис.
     * @return опис
     */
    public String getDescription() {
        return description;
    }

    /**
     * Встановлює опис.
     * @param description опис
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Повертає адресу.
     * @return адреса
     */
    public String getAddress() {
        return address;
    }

    /**
     * Встановлює адресу.
     * @param address адреса
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Повертає назву зображення профілю.
     * @return назва зображення профілю
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Встановлює назву зображення профілю.
     * @param icon назва зображення профілю
     */
    public void setIcon(final String icon) {
        this.icon = icon;
    }
}

package com.fluffy.spring.validation.forms;

import com.fluffy.spring.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Клас форми, що приймає дані реєстрації.
 * @author Сивоконь Вадим
 */
@Component
public class SignUpForm extends LogInForm {
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
    private String gender;

    /**
     * Дата народження.
     */
    private String bday;

    /**
     * Адреса.
     */
    private String address;

    /**
     * Створює порожній об'єкт (бін) форми.
     */
    public SignUpForm() { }

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
    public String getGender() {
        return gender;
    }

    /**
     * Встановлює гендер.
     * @param gender гендер
     */
    public void setGender(final String gender) {
        this.gender = gender;
    }

    /**
     * Повертає дату народження.
     * @return дата народження
     */
    public String getBday() {
        return bday;
    }

    /**
     * Встановлює дату народження.
     * @param bday дата народження
     */
    public void setBday(final String bday) {
        this.bday = bday;
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
     * {@inheritDoc}
     */
    @Override
    protected void translateData(final User newUser, final PasswordEncoder passwordEncoder) {
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setGender(gender.equals("male") ? User.Gender.MALE : User.Gender.FEMALE);
        super.translateData(newUser, passwordEncoder);

        if (bday != null && !bday.isEmpty()) {
            newUser.setBday(Date.valueOf(LocalDate.parse(bday, DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        }

        newUser.setAddress(address);
    }
}

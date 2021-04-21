package com.fluffy.spring.daos.exceptions;

/**
 * Клас винятку, що виникає у разі невдалої спроби маніпуляції даними у базі
 * даних.
 * @author Сивоконь Вадим
 */
public class PersistException extends RuntimeException {
    /**
     * Створює об'єкт винятку.
     */
    public PersistException() {
    }

    /**
     * Створює об'єкт винятку із можливістю вказання текстового повідомлення.
     * @param message текстове повідомлення
     */
    public PersistException(String message) {
        super(message);
    }

    /**
     * Створює об'єкт винятку із можливістю збереження текстового повідомлення
     * та більш конкретного винятку (його обгортання).
     * @param message текстове повідомлення
     * @param cause більш точна причина винятку
     */
    public PersistException(String message, Throwable cause) {
        super(message, cause);
    }
}

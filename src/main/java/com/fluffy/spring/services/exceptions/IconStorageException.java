package com.fluffy.spring.services.exceptions;

public class IconStorageException extends RuntimeException {
    public IconStorageException(String message) {
        super(message);
    }

    public IconStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

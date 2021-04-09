package com.fluffy.spring.services.exceptions;

public class IconStorageFileNotFoundException extends IconStorageException {
    public IconStorageFileNotFoundException(String message) {
        super(message);
    }

    public IconStorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

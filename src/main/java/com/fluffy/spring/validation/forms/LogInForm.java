package com.fluffy.spring.validation.forms;

public class LogInForm {
    private String email;

    public LogInForm(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

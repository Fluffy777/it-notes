package com.fluffy.spring.validation.forms;

public class LogInForm {
    private String email;
    private String password;

    public LogInForm() { }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

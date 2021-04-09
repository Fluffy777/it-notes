package com.fluffy.spring.validation.forms;

import com.fluffy.spring.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SignUpForm extends LogInForm {
    private String firstName;
    private String lastName;
    private String gender;
    private String bday;
    private String address;

    public SignUpForm() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    protected void translateData(User newUser, PasswordEncoder passwordEncoder) {
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

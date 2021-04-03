package com.fluffy.spring.domain;

import com.fluffy.spring.daos.Identified;

import java.sql.Date;

public class User implements Identified<Integer> {
    private Integer id;
    private Role role;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String password;
    private Date rday;
    private Date bday;
    private String description;
    private String address;
    private byte[] icon;

    enum Gender {
        MALE,
        FEMALE;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

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

    public Date getRday() {
        return rday;
    }

    public void setRday(Date rday) {
        this.rday = rday;
    }

    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
}

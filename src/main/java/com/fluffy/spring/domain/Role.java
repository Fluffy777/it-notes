package com.fluffy.spring.domain;

import com.fluffy.spring.daos.Identifiable;

public class Role implements Identifiable<Integer> {
    private Integer id;
    private String name;

    @Override
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

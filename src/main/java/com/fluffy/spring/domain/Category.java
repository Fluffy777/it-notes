package com.fluffy.spring.domain;

import com.fluffy.spring.daos.Identified;

public class Category implements Identified<Integer> {
    private Integer id;
    private String name;

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

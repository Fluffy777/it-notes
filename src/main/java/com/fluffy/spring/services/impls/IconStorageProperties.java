package com.fluffy.spring.services.impls;

import org.springframework.stereotype.Component;

@Component
public class IconStorageProperties {
    private String location = "icons";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

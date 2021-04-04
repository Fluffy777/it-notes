package com.fluffy.spring.daos;

import java.io.Serializable;

public interface Identifiable<PK extends Serializable> {
    PK getId();
}

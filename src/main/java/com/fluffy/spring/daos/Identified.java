package com.fluffy.spring.daos;

import java.io.Serializable;

public interface Identified<PK extends Serializable> {
    PK getId();
}

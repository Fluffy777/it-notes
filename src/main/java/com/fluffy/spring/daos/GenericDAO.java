package com.fluffy.spring.daos;

import java.io.Serializable;

public interface GenericDAO<T extends Identified<PK>, PK extends Serializable> {

}

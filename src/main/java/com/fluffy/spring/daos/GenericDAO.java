package com.fluffy.spring.daos;

import com.fluffy.spring.exceptions.PersistException;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T extends Identifiable<PK>, PK extends Serializable> {
    T create() throws PersistException;

    T persist(T object) throws PersistException;

    T getByPK(PK key) throws PersistException;

    T update(T object) throws PersistException;

    T delete(T object) throws PersistException;

    List<T> getAll() throws PersistException;
}

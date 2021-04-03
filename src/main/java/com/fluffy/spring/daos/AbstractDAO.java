package com.fluffy.spring.daos;

import java.sql.Connection;

public abstract class AbstractDAO<T extends Identified<PK>, PK extends Integer> implements GenericDAO<T, PK> {
    public abstract String getCreateQuery();
    public abstract String getSelectQuery();
    public abstract String getUpdateQuery();
    public abstract String getDeleteQuery();

    private Connection connection;
}

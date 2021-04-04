package com.fluffy.spring.daos;

import com.fluffy.spring.exceptions.PersistException;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Component
public abstract class AbstractDAO<T extends Identifiable<PK>, PK extends Integer> implements GenericDAO<T, PK> {
    public abstract String getCreateQuery();
    public abstract String getSelectQuery();
    public abstract String getUpdateQuery();
    public abstract String getDeleteQuery();

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws PersistException;
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistException;
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistException;

    private final ConnectionDAO connectionDAO;

    public AbstractDAO(ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    @Override
    public T create() throws PersistException {
        return null;
    }

    @Override
    public T persist(T object) throws PersistException {
        return null;
    }

    @Override
    public T getByPK(PK key) throws PersistException {
        return null;
    }

    @Override
    public T update(T object) throws PersistException {
        return null;
    }

    @Override
    public T delete(T object) throws PersistException {
        return null;
    }

    @Override
    public List<T> getAll() throws PersistException {
        return null;
    }
}

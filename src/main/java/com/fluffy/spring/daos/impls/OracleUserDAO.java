package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.AbstractDAO;
import com.fluffy.spring.domain.User;

public class OracleUserDAO extends AbstractDAO<User, Integer> {
    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getSelectQuery() {
        return null;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getDeleteQuery() {
        return null;
    }
}

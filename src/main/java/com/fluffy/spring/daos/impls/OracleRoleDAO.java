package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.AbstractDAO;
import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.RoleDAO;
import com.fluffy.spring.domain.Role;
import com.fluffy.spring.exceptions.PersistException;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleRoleDAO extends AbstractDAO<Role, Integer>  {
    private static class PersistRole extends Role {
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    public OracleRoleDAO(ConnectionDAO connectionDAO) {
        super(connectionDAO);
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO roles (name) VALUES (?)";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT role_id, name FROM roles";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE roles SET name = ? WHERE role_id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM roles WHERE role_id = ?";
    }

    @Override
    protected List<Role> parseResultSet(ResultSet resultSet) throws PersistException {
        List<Role> result = new LinkedList<>();
        try {
            PersistRole role = new PersistRole();
            role.setId(resultSet.getInt("role_id"));
            role.setName(resultSet.getString("name"));

            result.add(role);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return result;
    }

    private void prepareStatement0(PreparedStatement statement, Role object) throws SQLException {
        statement.setString(1, object.getName());
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Role object) throws PersistException {
        try {
            prepareStatement0(statement, object);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Role object) throws PersistException {
        try {
            prepareStatement0(statement, object);
            statement.setInt(2, object.getId());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }
}

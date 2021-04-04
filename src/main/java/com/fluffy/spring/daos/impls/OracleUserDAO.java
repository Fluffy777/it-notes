package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.AbstractDAO;
import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.RoleDAO;
import com.fluffy.spring.domain.Role;
import com.fluffy.spring.domain.User;
import com.fluffy.spring.exceptions.PersistException;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleUserDAO extends AbstractDAO<User, Integer> {
    private final RoleDAO roleDAO;

    private static class PersistUser extends User {
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    public OracleUserDAO(ConnectionDAO connectionDAO, RoleDAO roleDAO) {
        super(connectionDAO);
        this.roleDAO = roleDAO;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO users (role_id, enabled, first_name, last_name, gender, email, password, rday, bday, description, address, icon) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT user_id, role_id, enabled, first_name, last_name, gender, email, password, rday, bday, description, address, icon FROM users";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE users SET role_id = ?, enabled = ?, first_name = ?, last_name = ?, gender = ?, email = ?, password = ?, rday = ?, bday = ?, description = ?, address = ?, icon = ? WHERE user_id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM users WHERE user_id = ?";
    }

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) throws PersistException {
        List<User> result = new LinkedList<>();
        try {
            while (resultSet.next()) {
                PersistUser user = new PersistUser();
                user.setId(resultSet.getInt("user_id"));
                user.setRole((Role) roleDAO.getByPK(resultSet.getInt("role_id")));
                user.setEnabled(resultSet.getString("enabled").equals("y"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setGender(resultSet.getString("gender").equals("m") ? User.Gender.MALE : User.Gender.FEMALE);
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRday(resultSet.getDate("rday"));
                user.setBday(resultSet.getDate("bday"));
                user.setDescription(resultSet.getString("description"));
                // resultSet.getBlob("icon").getBytes(...)
                user.setIcon(null);

                result.add(user);
            }
        } catch (SQLException e) {
            // TODO: додатковий опис
            throw new PersistException(e);
        }
        return result;
    }

    private void prepareStatement0(PreparedStatement statement, User object) throws SQLException {
        statement.setInt(1, object.getRole().getId());
        statement.setString(2, object.isEnabled() ? "y" : "n");
        statement.setString(3, object.getFirstName());
        statement.setString(4, object.getLastName());
        statement.setString(5, object.isEnabled() ? "m" : "f");
        statement.setString(6, object.getEmail());
        statement.setString(7, object.getPassword());
        statement.setDate(8, object.getRday());
        statement.setDate(9, object.getBday());
        statement.setString(10, object.getDescription());
        statement.setBlob(11, (Blob) null);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistException {
        try {
            prepareStatement0(statement, object);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
        try {
            prepareStatement0(statement, object);
            statement.setInt(12, object.getId());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }
}

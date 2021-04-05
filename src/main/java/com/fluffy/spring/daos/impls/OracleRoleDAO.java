package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.RoleDAO;
import com.fluffy.spring.domain.Role;
import com.fluffy.spring.exceptions.DBConnectionException;
import com.fluffy.spring.exceptions.PersistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

public class OracleRoleDAO implements RoleDAO {
    private static final String QUERY_GET_ALL = "SELECT * FROM roles";
    private static final String QUERY_GET = QUERY_GET_ALL + " WHERE role_id = ?";
    private static final String QUERY_INSERT = "INSERT INTO roles (name) VALUES (?)";
    private static final String QUERY_UPDATE = "UPDATE roles SET name = ? WHERE role_id = ?";
    private static final String QUERY_DELETE = "DELETE FROM roles WHERE role_id = ?";
    private final ConnectionDAO connectionDAO;

    private Role newInstance(final ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setName(resultSet.getString("name"));
        return role;
    }

    public OracleRoleDAO(final ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    @Override
    public Role get(final int primaryKey) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET);
            int seq = 0;
            statement.setInt(++seq, primaryKey);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Role role = newInstance(resultSet);
                    if (resultSet.next()) {
                        throw new PersistException("Існує декілька ролей, у яких role_id = " + primaryKey);
                    }
                    return role;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про роль, у якої role_id = " + primaryKey, e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public List<Role> getAll() {
        try (Connection connection = connectionDAO.getConnection()) {
            Statement statement = connection.createStatement();
            try (ResultSet resultSet = statement.executeQuery(QUERY_GET_ALL)) {
                List<Role> roles = new LinkedList<>();
                while (resultSet.next()) {
                    roles.add(newInstance(resultSet));
                }
                return roles;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про всі ролі", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public void insert(final Role role) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
                int seq = 0;
                statement.setString(++seq, role.getName());

                if (statement.executeUpdate() == 0) {
                    throw new PersistException("Не вдалося додати інформацію про роль " + role.toString() + " до бази даних");
                }
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на додавання інформації про роль", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public void update(final int primaryKey, final Role role) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE)) {
                int seq = 0;
                statement.setString(++seq, role.getName());
                statement.setInt(++seq, primaryKey);

                if (statement.executeUpdate() == 0) {
                    throw new PersistException("Не вдалося оновити дані про роль " + role.toString() + " у базі даних");
                }
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на оновлення інформації про роль", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public void delete(final int primaryKey) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
                int seq = 0;
                statement.setInt(++seq, primaryKey);

                if (statement.executeUpdate() == 0) {
                    throw new PersistException("Не вдалося видалити дані про роль, у якої role_id = " + primaryKey);
                }
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на видалення інформації про роль");
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }
}

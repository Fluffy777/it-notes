package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.RoleDAO;
import com.fluffy.spring.daos.UserRoleDTO;
import com.fluffy.spring.domain.Role;
import com.fluffy.spring.exceptions.DBConnectionException;
import com.fluffy.spring.exceptions.PersistException;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class OracleUserRoleDTO implements UserRoleDTO {
    private static final String QUERY_GET_ALL_BY_USER = "SELECT * FROM user_role WHERE user_id = ?";
    private final ConnectionDAO connectionDAO;
    private final RoleDAO roleDAO;

    public OracleUserRoleDTO(final ConnectionDAO connectionDAO, final RoleDAO roleDAO) {
        this.connectionDAO = connectionDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public Set<Role> getAllByUserId(final int userId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_ALL_BY_USER);
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                Set<Role> roles = new HashSet<>();
                while (resultSet.next()) {
                    roles.add(roleDAO.getById(resultSet.getInt("role_id")));
                }
                return roles;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про всі ролі користувача ", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }
}

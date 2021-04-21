package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.RoleDAO;
import com.fluffy.spring.daos.UserRoleDAO;
import com.fluffy.spring.domain.Role;
import com.fluffy.spring.daos.exceptions.DBConnectionException;
import com.fluffy.spring.daos.exceptions.PersistException;
import com.fluffy.spring.domain.UserRole;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас біна, що дозволяє безпосередньо отримувати інформацію про ролі
 * користувачів із бази даних Oracle.
 * @author Сивоконь Вадим
 */
@Component
public class OracleUserRoleDAO implements UserRoleDAO {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(OracleUserRoleDAO.class);

    /**
     * Запит на отримання всіх ролей користувача за його ID.
     */
    private static final String QUERY_GET_ALL_BY_USER = "SELECT * FROM user_role WHERE user_id = ?";

    /**
     * Запит на збереження моделі ролі користувача у базі даних.
     */
    private static final String QUERY_INSERT = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";

    /**
     * Бін для отримання підключення до бази даних.
     */
    private final ConnectionDAO connectionDAO;

    /**
     * Бін для отримання інформації про ролі.
     */
    private RoleDAO roleDAO;

    /**
     * Створює бін для отримання інформації про ролі користувача.
     * @param connectionDAO бін для отримання підключення до бази даних
     */
    public OracleUserRoleDAO(final ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    /**
     * Autowiring біна для отримання інформації про ролі.
     * @param roleDAO бін для отримання інформації про ролі
     */
    @Autowired
    public void setRoleDAO(final RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    /**
     * {@inheritDoc}
     */
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
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про всі ролі користувача, у якого user_id = " + userId, e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про всі ролі користувача, у якого user_id = " + userId, e);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRole insert(final UserRole userRole) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
                int seq = 0;
                statement.setInt(++seq, userRole.getUser().getId());
                statement.setInt(++seq, userRole.getRole().getId());

                if (statement.executeUpdate() > 0) {
                    return userRole;
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на додавання інформації про роль користувача", e);
                throw new PersistException("Не вдалося виконати запит на додавання інформації про роль користувача", e);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
        return null;
    }
}

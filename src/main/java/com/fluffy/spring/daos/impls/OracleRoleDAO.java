package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.RoleDAO;
import com.fluffy.spring.domain.Role;
import com.fluffy.spring.daos.exceptions.DBConnectionException;
import com.fluffy.spring.daos.exceptions.PersistException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Клас біна, що дозволяє безпосередньо отримувати інформацію про ролі із бази
 * даних Oracle.
 * @author Сивоконь Вадим
 */
@Component
public class OracleRoleDAO implements RoleDAO {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(OracleRoleDAO.class);

    /**
     * Запит на отримання всіх ролей.
     */
    private static final String QUERY_GET_ALL = "SELECT * FROM roles";

    /**
     * Запит на отримання ролі із ID.
     */
    private static final String QUERY_GET = QUERY_GET_ALL + " WHERE role_id = ?";

    /**
     * Запит на отримання ролі за назвою.
     */
    private static final String QUERY_GET_BY_NAME = QUERY_GET_ALL + " WHERE name = ?";

    /**
     * Бін для отримання підключення до бази даних.
     */
    private final ConnectionDAO connectionDAO;

    private Role newInstance(final ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getInt("role_id"));
        role.setName(resultSet.getString("name"));
        return role;
    }

    /**
     * Створює бін для отримання інформації про ролі.
     * @param connectionDAO бін для отримання підключення до бази даних
     */
    public OracleRoleDAO(final ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getById(final int roleId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET);
            int seq = 0;
            statement.setInt(++seq, roleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Role role = newInstance(resultSet);
                    if (resultSet.next()) {
                        logger.log(Level.ERROR, "Існує декілька ролей, у яких role_id = " + roleId);
                        throw new PersistException("Існує декілька ролей, у яких role_id = " + roleId);
                    }
                    return role;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про роль, у якої role_id = " + roleId, e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про роль, у якої role_id = " + roleId, e);
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
    public Role getByName(final String name) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_BY_NAME);
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Role role = newInstance(resultSet);
                    if (resultSet.next()) {
                        logger.log(Level.ERROR, "Існує декілька ролей, у яких name = " + name);
                        throw new PersistException("Існує декілька ролей, у яких name = " + name);
                    }
                    return role;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про роль, у якої name = " + name, e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про роль, у якої name = " + name, e);
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
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про всі ролі", e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про всі ролі", e);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }
}

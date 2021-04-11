package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.UserDAO;
import com.fluffy.spring.daos.UserRoleDTO;
import com.fluffy.spring.domain.User;
import com.fluffy.spring.daos.exceptions.DBConnectionException;
import com.fluffy.spring.daos.exceptions.PersistException;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleUserDAO implements UserDAO {
    private static final String QUERY_GET_ALL = "SELECT * FROM users";
    private static final String QUERY_GET = QUERY_GET_ALL + " WHERE user_id = ?";
    private static final String QUERY_GET_BY_EMAIL =  QUERY_GET_ALL + " WHERE email = ?";
    private static final String QUERY_INSERT = "INSERT INTO users (user_id, enabled, first_name, last_name, gender, email, password, rday, bday, description, address, icon) VALUES (users_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String QUERY_UPDATE = "UPDATE users SET enabled = ?, first_name = ?, last_name = ?, gender = ?, email = ?, password = ?, rday = ?, bday = ?, description = ?, address = ?, icon = ? WHERE user_id = ?";
    private static final String QUERY_DELETE = "DELETE FROM users WHERE user_id = ?";
    private final UserRoleDTO userRoleDTO;
    private final ConnectionDAO connectionDAO;

    private User newInstance(final ResultSet resultSet) throws SQLException {
        User user = new User();
        int userId = resultSet.getInt("user_id");
        user.setId(userId);
        // roles
        user.setRoles(userRoleDTO.getAllByUserId(userId));
        user.setEnabled(resultSet.getString("enabled").equals("Y"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setGender(resultSet.getString("gender").equals("M") ? User.Gender.MALE : User.Gender.FEMALE);
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRday(resultSet.getDate("rday"));
        user.setBday(resultSet.getDate("bday"));
        user.setDescription(resultSet.getString("description"));
        user.setAddress(resultSet.getString("address"));
        user.setIcon(resultSet.getString("icon"));
        return user;
    }

    public OracleUserDAO(final ConnectionDAO connectionDAO, final UserRoleDTO userRoleDTO) {
        this.connectionDAO = connectionDAO;
        this.userRoleDTO = userRoleDTO;
    }

    @Override
    public User getById(final int userId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET);
            int seq = 0;
            statement.setInt(++seq, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = newInstance(resultSet);
                    if (resultSet.next()) {
                        throw new PersistException("Існує декілька користувачів, у яких user_id = " + userId);
                    }
                    return user;
                }
                return null;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про користувача, у якого user_id = " + userId, e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = connectionDAO.getConnection()) {
            Statement statement = connection.createStatement();
            try (ResultSet resultSet = statement.executeQuery(QUERY_GET_ALL)) {
                List<User> users = new LinkedList<>();
                while (resultSet.next()) {
                    users.add(newInstance(resultSet));
                }
                return users;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про всі ролі", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public User getByEmail(final String email) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_BY_EMAIL);
            int seq = 0;
            statement.setString(++seq, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = newInstance(resultSet);
                    if (resultSet.next()) {
                        throw new PersistException("Існує декілька користувачів, у яких email = " + email);
                    }
                    return user;
                }
                return null;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про користувача, у якого email = " + email, e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public User insert(final User user) {
        try (Connection connection = connectionDAO.getConnection()) {
            String[] columns = new String[]{"USER_ID", "ENABLED", "FIRST_NAME", "LAST_NAME", "GENDER", "EMAIL", "PASSWORD", "RDAY", "BDAY", "DESCRIPTION", "ADDRESS", "ICON"};
            try (PreparedStatement statement = connection.prepareStatement(QUERY_INSERT, columns)) {
                int seq = 0;
                statement.setString(++seq, user.isEnabled() ? "Y" : "N");
                statement.setString(++seq, user.getFirstName());
                statement.setString(++seq, user.getLastName());
                statement.setString(++seq, user.getGender().equals(User.Gender.MALE) ? "M" : "F");
                statement.setString(++seq, user.getEmail());
                statement.setString(++seq, user.getPassword());
                statement.setDate(++seq, user.getRday());
                statement.setDate(++seq, user.getBday());
                statement.setString(++seq, user.getDescription());
                statement.setString(++seq, user.getAddress());
                statement.setString(++seq, user.getIcon());

                if (statement.executeUpdate() > 0) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    resultSet.next();
                    user.setId(resultSet.getInt(1));
                }
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на додавання інформації про користувача", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
        return user;
    }

    @Override
    public User update(final int primaryKey, final User user) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE)) {
                int seq = 0;
                statement.setString(++seq, user.isEnabled() ? "Y" : "N");
                statement.setString(++seq, user.getFirstName());
                statement.setString(++seq, user.getLastName());
                statement.setString(++seq, user.getGender().equals(User.Gender.MALE) ? "M" : "F");
                statement.setString(++seq, user.getEmail());
                statement.setString(++seq, user.getPassword());
                statement.setDate(++seq, user.getRday());
                statement.setDate(++seq, user.getBday());
                statement.setString(++seq, user.getDescription());
                statement.setString(++seq, user.getAddress());
                statement.setString(++seq, user.getIcon());
                statement.setInt(++seq, primaryKey);

                if (statement.executeUpdate() > 0) {
                    return user;
                }
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на оновлення інформації про користувача", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
        return null;
    }

    @Override
    public boolean delete(final int primaryKey) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
                int seq = 0;
                statement.setInt(++seq, primaryKey);

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на видалення інформації про користувача");
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }
}

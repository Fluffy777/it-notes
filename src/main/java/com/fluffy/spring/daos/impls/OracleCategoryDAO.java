package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.CategoryDAO;
import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.exceptions.DBConnectionException;
import com.fluffy.spring.daos.exceptions.PersistException;
import com.fluffy.spring.domain.Category;
import oracle.jdbc.OraclePreparedStatement;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleCategoryDAO implements CategoryDAO {
    private static final String QUERY_GET_ALL = "SELECT * FROM categories";
    private static final String QUERY_GET = QUERY_GET_ALL + " WHERE category_id = ?";
    private static final String QUERY_GET_BY_NAME = QUERY_GET_ALL + " WHERE name = ?";
    private static final String QUERY_INSERT = "INSERT INTO categories (category_id, name) VALUES (categories_seq.nextval, ?)";
    private final ConnectionDAO connectionDAO;

    private Category newInstance(final ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("category_id"));
        category.setName(resultSet.getString("name"));
        return category;
    }

    public OracleCategoryDAO(ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    @Override
    public Category getById(int categoryId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET);
            int seq = 0;
            statement.setInt(++seq, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Category category = newInstance(resultSet);
                    if (resultSet.next()) {
                        throw new PersistException("Існує декілька категорій, у яких category_id = " + categoryId);
                    }
                    return category;
                }
                return null;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про категорію, у якої category_id = " + categoryId, e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public Category getByName(String name) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_BY_NAME);
            int seq = 0;
            statement.setString(++seq, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Category category = newInstance(resultSet);
                    if (resultSet.next()) {
                        throw new PersistException("Існує декілька категорій, у яких name = " + name);
                    }
                    return category;
                }
                return null;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про категорію, у якої name = " + name, e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public List<Category> getAll() {
        try (Connection connection = connectionDAO.getConnection()) {
            Statement statement = connection.createStatement();
            try (ResultSet resultSet = statement.executeQuery(QUERY_GET_ALL)) {
                List<Category> categories = new LinkedList<>();
                while (resultSet.next()) {
                    categories.add(newInstance(resultSet));
                }
                return categories;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про всі категорії", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public Category insert(Category category) {
        try (Connection connection = connectionDAO.getConnection()) {
            String[] cols = new String[] {"CATEGORY_ID", "NAME"};
            try (OraclePreparedStatement statement = (OraclePreparedStatement) connection.prepareStatement(QUERY_INSERT, cols)) {
                int seq = 0;
                statement.setString(++seq, category.getName());

                int affectedRowsCount = statement.executeUpdate();
                if (affectedRowsCount > 0) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    resultSet.next();

                    category.setId(resultSet.getInt(1));
                    System.out.println("new: " + category.getId());
                }

            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на додавання інформації про категорію", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
        return category;
    }
}

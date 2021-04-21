package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.ArticleDAO;
import com.fluffy.spring.daos.CategoryDAO;
import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.exceptions.DBConnectionException;
import com.fluffy.spring.daos.exceptions.PersistException;
import com.fluffy.spring.domain.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Клас біна, що дозволяє безпосередньо отримувати інформацію про категорії із
 * бази даних Oracle.
 * @author Сивоконь Вадим
 */
@Component
public class OracleCategoryDAO implements CategoryDAO {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(OracleCategoryDAO.class);

    /**
     * Запит на отримання всіх категорій.
     */
    private static final String QUERY_GET_ALL = "SELECT * FROM categories";

    /**
     * Запит на отримання категорії із ID.
     */
    private static final String QUERY_GET = QUERY_GET_ALL + " WHERE category_id = ?";

    /**
     * Запит на отримання категорії із певною назвою.
     */
    private static final String QUERY_GET_BY_NAME = QUERY_GET_ALL + " WHERE name = ?";

    /**
     * Запит на збереження моделі категорії у базі даних.
     */
    private static final String QUERY_INSERT = "INSERT INTO categories (category_id, name) VALUES (categories_seq.nextval, ?)";

    /**
     * Запит на оновлення моделі категорії у базі даних.
     */
    private static final String QUERY_UPDATE = "UPDATE categories SET name = ? WHERE category_id = ?";

    /**
     * Запит на видалення моделі категорії із бази даних за ID.
     */
    private static final String QUERY_DELETE = "DELETE FROM categories WHERE category_id = ?";

    /**
     * Бін для отримання підключення до бази даних.
     */
    private final ConnectionDAO connectionDAO;

    /**
     * Бін для отримання інформації про статті.
     */
    private ArticleDAO articleDAO;

    private Category newInstance(final ResultSet resultSet) throws SQLException {
        Category category = new Category();
        int categoryId = resultSet.getInt("category_id");

        category.setId(categoryId);
        category.setName(resultSet.getString("name"));
        category.setAmountOfArticles(articleDAO.getAmountOfArticlesByCategoryId(categoryId));
        return category;
    }

    /**
     * Створює бін для отримання інформації про категорії.
     * @param connectionDAO бін для отримання підключеня до бази даних
     */
    public OracleCategoryDAO(final ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    /**
     * Autowiring біна для отримання інформації про статті.
     * @param articleDAO бін для отримання інформації про статті
     */
    @Autowired
    public void setArticleDAO(final ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category getById(final int categoryId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET);
            int seq = 0;
            statement.setInt(++seq, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Category category = newInstance(resultSet);
                    if (resultSet.next()) {
                        logger.log(Level.ERROR, "Існує декілька категорій, у яких category_id = " + categoryId);
                        throw new PersistException("Існує декілька категорій, у яких category_id = " + categoryId);
                    }
                    return category;
                }
                return null;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про категорію, у якої category_id = " + categoryId, e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про категорію, у якої category_id = " + categoryId, e);
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
    public Category getByName(final String name) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_BY_NAME);
            int seq = 0;
            statement.setString(++seq, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Category category = newInstance(resultSet);
                    if (resultSet.next()) {
                        logger.log(Level.ERROR, "Існує декілька категорій, у яких name = " + name);
                        throw new PersistException("Існує декілька категорій, у яких name = " + name);
                    }
                    return category;
                }
                return null;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про категорію, у якої name = " + name, e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про категорію, у якої name = " + name, e);
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
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про всі категорії", e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про всі категорії", e);
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
    public Category insert(final Category category) {
        try (Connection connection = connectionDAO.getConnection()) {
            String[] columns = new String[] {"CATEGORY_ID", "NAME"};
            try (PreparedStatement statement = connection.prepareStatement(QUERY_INSERT, columns)) {
                int seq = 0;
                statement.setString(++seq, category.getName());

                if (statement.executeUpdate() > 0) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    resultSet.next();
                    category.setId(resultSet.getInt(1));
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на додавання інформації про категорію", e);
                throw new PersistException("Не вдалося виконати запит на додавання інформації про категорію", e);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
        return category;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category update(final int categoryId, final Category category) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE)) {
                int seq = 0;
                statement.setString(++seq, category.getName());
                statement.setInt(++seq, categoryId);

                if (statement.executeUpdate() > 0) {
                    return category;
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на оновлення інформації про категорію", e);
                throw new PersistException("Не вдалося виконати запит на оновлення інформації про категорію", e);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final int categoryId) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
                int seq = 0;
                statement.setInt(++seq, categoryId);

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на видалення інформації про категорію", e);
                throw new PersistException("Не вдалося виконати запит на видалення інформації про категорію", e);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }
}

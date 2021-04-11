package com.fluffy.spring.daos.impls;

import com.fluffy.spring.controllers.AuthController;
import com.fluffy.spring.daos.ArticleDAO;
import com.fluffy.spring.daos.CategoryDAO;
import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.UserDAO;
import com.fluffy.spring.daos.exceptions.DBConnectionException;
import com.fluffy.spring.daos.exceptions.PersistException;
import com.fluffy.spring.domain.Article;
import com.fluffy.spring.domain.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleArticleDAO implements ArticleDAO {
    private static final String QUERY_GET_ALL = "SELECT * FROM articles";
    private static final String QUERY_GET = QUERY_GET_ALL + " WHERE article_id = ?";
    private static final String QUERY_INSERT = "INSERT INTO articles (article_id, category_id, user_id, name, content, modification_date) VALUES (articles_seq.nextval, ?, ?, ?, ?, ?)";
    private static final String QUERY_UPDATE = "UPDATE articles SET user_id = ?, name = ?, content = ?, modification_date = ? WHERE article_id = ?";
    private static final String QUERY_DELETE = "DELETE FROM articles WHERE article_id = ?";
    private static final String QUERY_GET_ALL_BY_CATEGORY = QUERY_GET_ALL + " WHERE category_id = ?";
    private static final String QUERY_GET_MOST_POPULAR = "SELECT * FROM (" + QUERY_GET_ALL + " ORDER BY views DESC) WHERE ROWNUM <= ?";

    private final ConnectionDAO connectionDAO;
    private final CategoryDAO categoryDAO;
    private final UserDAO userDAO;

    public OracleArticleDAO(ConnectionDAO connectionDAO, CategoryDAO categoryDAO, UserDAO userDAO) {
        this.connectionDAO = connectionDAO;
        this.categoryDAO = categoryDAO;
        this.userDAO = userDAO;
    }

    private Article newInstance(final ResultSet resultSet) throws SQLException {
        Article article = new Article();
        article.setId(resultSet.getInt("article_id"));
        article.setCategory(categoryDAO.getById(resultSet.getInt("category_id")));
        article.setUser(userDAO.getByEmail(AuthController.getCurrentUsername()));
        article.setName(resultSet.getString("name"));
        article.setContent(resultSet.getString("content"));
        article.setModificationDate(resultSet.getDate("modification_date"));
        return article;
    }

    @Override
    public Article getById(int articleId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET);
            int seq = 0;
            statement.setInt(++seq, articleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Article article = newInstance(resultSet);
                    if (resultSet.next()) {
                        throw new PersistException("Існує декілька статтей, у яких article_id = " + articleId);
                    }
                    return article;
                }
                return null;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про статтю, у якої article_id = " + articleId, e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public List<Article> getAll() {
        try (Connection connection = connectionDAO.getConnection()) {
            Statement statement = connection.createStatement();
            try (ResultSet resultSet = statement.executeQuery(QUERY_GET_ALL)) {
                List<Article> articles = new LinkedList<>();
                while (resultSet.next()) {
                    articles.add(newInstance(resultSet));
                }
                return articles;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про всі статті", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public List<Article> getAllByCategoryId(int categoryId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_ALL_BY_CATEGORY);
            statement.setInt(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Article> articles = new LinkedList<>();
                while (resultSet.next()) {
                    articles.add(newInstance(resultSet));
                }
                return articles;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про всі статті із category_id = " + categoryId, e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public Article insert(Article article) {
        try (Connection connection = connectionDAO.getConnection()) {
            String[] columns = new String[] {"ARTICLE_ID", "CATEGORY_ID", "USER_ID", "NAME", "CONTENT", "MODIFICATION_DATE"};
            try (PreparedStatement statement = connection.prepareStatement(QUERY_INSERT, columns)) {
                int seq = 0;
                statement.setInt(++seq, article.getCategory().getId());
                statement.setInt(++seq, article.getUser().getId());
                statement.setString(++seq, article.getName());
                statement.setString(++seq, article.getContent());
                statement.setDate(++seq, article.getModificationDate());

                if (statement.executeUpdate() > 0) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    resultSet.next();
                    article.setId(resultSet.getInt(1));
                }
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на додавання інформації про статтю", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
        return article;
    }

    @Override
    public Article update(int articleId, Article article) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE)) {
                int seq = 0;
                statement.setInt(++seq, article.getUser().getId());
                statement.setString(++seq, article.getName());
                statement.setString(++seq, article.getContent());
                statement.setDate(++seq, article.getModificationDate());
                statement.setInt(++seq, articleId);

                if (statement.executeUpdate() > 0) {
                    return article;
                }
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на оновлення інформації про статтю", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
        return null;
    }

    @Override
    public boolean delete(int articleId) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
                int seq = 0;
                statement.setInt(++seq, articleId);

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на видалення інформації про статтю", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    @Override
    public List<Article> getMostPopular(int topSize) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_MOST_POPULAR);
            statement.setInt(1, topSize);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Article> articles = new LinkedList<>();
                while (resultSet.next()) {
                    articles.add(newInstance(resultSet));
                }
                return articles;
            } catch (SQLException e) {
                throw new PersistException("Не вдалося виконати запит на отримання даних про найбільш популярні статті (" + topSize + ")", e);
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }
}

package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.ArticleDAO;
import com.fluffy.spring.daos.CommentDAO;
import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.UserDAO;
import com.fluffy.spring.daos.exceptions.DBConnectionException;
import com.fluffy.spring.daos.exceptions.PersistException;
import com.fluffy.spring.domain.Comment;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

/**
 * Клас біна, що дозволяє безпосередньо отримувати інформацію про коментарі із
 * бази даних Oracle.
 * @author Сивоконь Вадим
 */
@Component
public class OracleCommentDAO implements CommentDAO {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(OracleCommentDAO.class);

    /**
     * Запит на отримання всіх коментарів.
     */
    private static final String QUERY_GET_ALL = "SELECT * FROM comments";

    /**
     * Запит на отримання всіх коментарів до статті із ID.
     */
    private static final String QUERY_GET_ALL_BY_ARTICLE = QUERY_GET_ALL + " WHERE article_id = ? ORDER BY local_id";

    /**
     * Запит на отримання коментаря із ID.
     */
    private static final String QUERY_GET = QUERY_GET_ALL + " WHERE comment_id = ?";

    /**
     * Запит на збереження моделі коментаря у базі даних.
     */
    private static final String QUERY_INSERT = "INSERT INTO comments (comment_id, article_id, local_id, parent_id, user_id, content, modification_date) VALUES (comments_seq.nextval, ?, ?, ?, ?, ?, ?)";

    /**
     * Запит на оновлення моделі коментаря у базі даних.
     */
    private static final String QUERY_UPDATE = "UPDATE comments SET article_id = ?, local_id = ?, parent_id = ?, user_id = ?, content = ?, modification_date = ? WHERE comment_id = ?";

    /**
     * Запит на видалення моделі коментаря із бази даних за ID.
     */
    private static final String QUERY_DELETE = "DELETE FROM comments WHERE comment_id = ?";

    /**
     * Бін для отримання підключення до бази даних.
     */
    private final ConnectionDAO connectionDAO;

    /**
     * Бін для отримання інформації про статті.
     */
    private ArticleDAO articleDAO;

    /**
     * Бін для отримання інформації про користувачів.
     */
    private UserDAO userDAO;

    private Comment newInstance(final ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getInt("comment_id"));
        comment.setArticle(articleDAO.getById(resultSet.getInt("article_id")));
        comment.setLocalId(resultSet.getInt("local_id"));

        Integer parentId = resultSet.getInt("parent_id");
        if (resultSet.wasNull()) {
            comment.setParentId(null);
        } else {
            comment.setParentId(parentId);
        }

        comment.setUser(userDAO.getById(resultSet.getInt("user_id")));
        comment.setContent(resultSet.getString("content"));
        comment.setModificationDate(resultSet.getDate("modification_date"));
        return comment;
    }

    /**
     * Створює бін для отримання інформації про коментарі.
     * @param connectionDAO бін для отримання підключення до бази даних
     */
    public OracleCommentDAO(final ConnectionDAO connectionDAO) {
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
     * Autowiring біна для отримання інформації про користувачів.
     * @param userDAO бін для отримання інформації про користувачів
     */
    @Autowired
    public void setUserDAO(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comment getById(final int commentId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET);
            statement.setInt(1, commentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Comment comment = newInstance(resultSet);
                    if (resultSet.next()) {
                        logger.log(Level.ERROR, "Існує декілька коментарів, у яких comment_id = " + commentId);
                        throw new PersistException("Існує декілька коментарів, у яких comment_id = " + commentId);
                    }
                    return comment;
                }
                return null;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про коментар, у якого comment_id = " + commentId, e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про коментар, у якого comment_id = " + commentId, e);
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
    public List<Comment> getAllByArticleId(final int articleId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_ALL_BY_ARTICLE);
            statement.setInt(1, articleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Comment> comments = new LinkedList<>();
                while (resultSet.next()) {
                    comments.add(newInstance(resultSet));
                }
                return comments;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання всіх коментарів до статті, у якої article_id = " + articleId, e);
                throw new PersistException("Не вдалося виконати запит на отримання всіх коментарів до статті, у якої article_id = " + articleId, e);
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
    public Comment insert(final Comment comment) {
        try (Connection connection = connectionDAO.getConnection()) {
            String[] columns = new String[] {"COMMENT_ID", "ARTICLE_ID", "LOCAL_ID", "PARENT_ID", "USER_ID", "CONTENT", "MODIFICATION_DATE"};
            try (PreparedStatement statement = connection.prepareStatement(QUERY_INSERT, columns)) {
                int seq = 0;
                statement.setInt(++seq, comment.getArticle().getId());
                statement.setInt(++seq, comment.getLocalId());

                Integer parentId = comment.getParentId();
                if (parentId != null) {
                    statement.setInt(++seq, parentId);
                } else {
                    statement.setNull(++seq, Types.INTEGER);
                }

                statement.setInt(++seq, comment.getUser().getId());
                statement.setString(++seq, comment.getContent());
                statement.setDate(++seq, comment.getModificationDate());

                if (statement.executeUpdate() > 0) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    resultSet.next();
                    comment.setId(resultSet.getInt(1));
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на додавання інформації про коментар", e);
                throw new PersistException("Не вдалося виконати запит на додавання інформації про коментар", e);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
        return comment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comment update(final int commentId, final Comment comment) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE)) {
                int seq = 0;
                statement.setInt(++seq, comment.getArticle().getId());
                statement.setInt(++seq, comment.getLocalId());

                Integer parentId = comment.getParentId();
                if (parentId != null) {
                    statement.setInt(++seq, parentId);
                } else {
                    statement.setNull(++seq, Types.INTEGER);
                }

                statement.setInt(++seq, comment.getUser().getId());
                statement.setString(++seq, comment.getContent());
                statement.setDate(++seq, comment.getModificationDate());
                statement.setInt(++seq, commentId);

                if (statement.executeUpdate() > 0) {
                    return comment;
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на оновлення інформації про коментар", e);
                throw new PersistException("Не вдалося виконати запит на оновлення інформації про коментар", e);
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
    public boolean delete(final int commentId) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
                int seq = 0;
                statement.setInt(++seq, commentId);

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на видалення інформації про коментар", e);
                throw new PersistException("Не вдалося виконати запит на видалення інформації про коментар", e);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }
}

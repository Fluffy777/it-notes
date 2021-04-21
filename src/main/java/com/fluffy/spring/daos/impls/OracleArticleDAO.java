package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.ArticleDAO;
import com.fluffy.spring.daos.CategoryDAO;
import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.UserDAO;
import com.fluffy.spring.daos.exceptions.DBConnectionException;
import com.fluffy.spring.daos.exceptions.PersistException;
import com.fluffy.spring.domain.Article;
import com.fluffy.spring.domain.Category;
import com.fluffy.spring.domain.FilteringArticle;
import com.fluffy.spring.domain.User;
import com.fluffy.spring.util.FilterOperationMapperUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Клас біна, що дозволяє безпосередньо отримувати інформацію про статті із
 * бази даних Oracle.
 * @author Сивоконь Вадим
 */
@Component
public class OracleArticleDAO implements ArticleDAO {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(OracleArticleDAO.class);

    /**
     * Запит на отримання всіх статтей.
     */
    private static final String QUERY_GET_ALL = "SELECT * FROM articles";

    /**
     * Запит на отримання всіх статтей із обмеженням у кількості символів (превью) та можливою додатковою фільтрацією.
     */
    private static final String QUERY_GET_ALL_WITH_PREVIEW = "SELECT articles.article_id, articles.category_id, articles.user_id, articles.name, CASE WHEN LENGTH(articles.content) > 200 THEN SUBSTR(articles.content, 1, 200)||'...' ELSE articles.content END AS content, modification_date, views FROM articles JOIN categories ON (categories.category_id = articles.category_id) JOIN users ON (users.user_id = articles.user_id)";

    /**
     * Запит на отримання статті за ID.
     */
    private static final String QUERY_GET = QUERY_GET_ALL + " WHERE article_id = ?";

    /**
     * Запит на отримання кількості статей у категорії із ID.
     */
    private static final String QUERY_GET_AMOUNT_OF_ARTICLES_BY_CATEGORY = "SELECT COUNT(*) amountOfArticles FROM articles WHERE category_id = ?";

    /**
     * Запит на збереження моделі статті у базі даних.
     */
    private static final String QUERY_INSERT = "INSERT INTO articles (article_id, category_id, user_id, name, content, modification_date) VALUES (articles_seq.nextval, ?, ?, ?, ?, ?)";

    /**
     * Запит на оновлення моделі статті у базі даних.
     */
    private static final String QUERY_UPDATE = "UPDATE articles SET category_id = ?, user_id = ?, name = ?, content = ?, modification_date = ?, views = ? WHERE article_id = ?";

    /**
     * Запит на видалення моделі статті у базі даних за ID.
     */
    private static final String QUERY_DELETE = "DELETE FROM articles WHERE article_id = ?";

    /**
     * Запит на отримання найбільш популярних статей.
     */
    private static final String QUERY_GET_MOST_POPULAR = "SELECT * FROM (" + QUERY_GET_ALL + " ORDER BY views DESC) WHERE ROWNUM <= ?";

    /**
     * Запит на отримання подібних за категорією статей.
     */
    private static final String QUERY_GET_SAME = "SELECT * FROM (" + QUERY_GET_ALL + " WHERE category_id = (SELECT category_id FROM articles WHERE article_id = ?) AND article_id != ? ORDER BY modification_date DESC) WHERE ROWNUM <= ?";

    /**
     * Запит на отримання останнього використаного локального ID коментаря для
     * статті із ID.
     */
    private static final String QUERY_GET_LAST_COMMENT_LOCAL_ID = "SELECT NVL(MAX(local_id), 0) AS local_id FROM comments WHERE article_id = ?";

    /**
     * Запит на збільшення кількості переглядів (+1) статті (її часткове
     * оновлення).
     */
    private static final String QUERY_INCREASE_VIEWS = "UPDATE articles SET views = views + 1 WHERE article_id = ?";

    /**
     * Бін для отримання підключення до бази даних.
     */
    private final ConnectionDAO connectionDAO;

    /**
     * Бін для отримання інформації про категорії.
     */
    private CategoryDAO categoryDAO;

    /**
     * Бін для отримання інформації про користувачів.
     */
    private UserDAO userDAO;

    /**
     * Створює бін для отримання інформації про статті.
     * @param connectionDAO бін для отримання підключення до бази даних
     */
    public OracleArticleDAO(final ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    /**
     * Autowiring біна для отримання інформації про категорії.
     * @param categoryDAO бін для отримання інформації про категорії
     */
    @Autowired
    public void setCategoryDAO(final CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    /**
     * Autowiring біна для отримання інформації про користувачів.
     * @param userDAO бін для отримання інформації про користувачів
     */
    @Autowired
    public void setUserDAO(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private Article newInstance(final ResultSet resultSet) throws SQLException {
        Article article = new Article();
        article.setId(resultSet.getInt("article_id"));
        article.setCategory(categoryDAO.getById(resultSet.getInt("category_id")));
        article.setUser(userDAO.getById(resultSet.getInt("user_id")));
        article.setName(resultSet.getString("name"));
        article.setContent(resultSet.getString("content"));
        article.setModificationDate(resultSet.getDate("modification_date"));
        article.setViews(resultSet.getInt("views"));
        return article;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Article getById(final int articleId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET);
            int seq = 0;
            statement.setInt(++seq, articleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Article article = newInstance(resultSet);
                    if (resultSet.next()) {
                        logger.log(Level.ERROR, "Існує декілька статей, у яких article_id = " + articleId);
                        throw new PersistException("Існує декілька статей, у яких article_id = " + articleId);
                    }
                    return article;
                }
                return null;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про статтю, у якої article_id = " + articleId, e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про статтю, у якої article_id = " + articleId, e);
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
    public int getAmountOfArticlesByCategoryId(final int categoryId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_AMOUNT_OF_ARTICLES_BY_CATEGORY);
            statement.setInt(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("amountOfArticles");
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання кількості статей із category_id = " + categoryId, e);
                throw new PersistException("Не вдалося виконати запит на отримання кількості статей із category_id = " + categoryId, e);
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
    public Article insert(final Article article) {
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
                logger.log(Level.ERROR, "Не вдалося виконати запит на додавання інформації про статтю", e);
                throw new PersistException("Не вдалося виконати запит на додавання інформації про статтю", e);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
        return article;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Article update(final int articleId, final Article article) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE)) {
                int seq = 0;
                statement.setInt(++seq, article.getCategory().getId());
                statement.setInt(++seq, article.getUser().getId());
                statement.setString(++seq, article.getName());
                statement.setString(++seq, article.getContent());
                statement.setDate(++seq, article.getModificationDate());
                statement.setInt(++seq, article.getViews());
                statement.setInt(++seq, articleId);

                if (statement.executeUpdate() > 0) {
                    return article;
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на оновлення інформації про статтю", e);
                throw new PersistException("Не вдалося виконати запит на оновлення інформації про статтю", e);
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
    public boolean delete(final int articleId) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
                int seq = 0;
                statement.setInt(++seq, articleId);

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на видалення інформації про статтю", e);
                throw new PersistException("Не вдалося виконати запит на видалення інформації про статтю", e);
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
    public List<Article> getMostPopular(final int topSize) {
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
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про найбільш популярні статті (" + topSize + ")", e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про найбільш популярні статті (" + topSize + ")", e);
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
    public List<Article> getSame(final int articleId, final int maxCount) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_SAME);
            int seq = 0;
            statement.setInt(++seq, articleId);
            statement.setInt(++seq, articleId);
            statement.setInt(++seq, maxCount);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Article> articles = new LinkedList<>();
                while (resultSet.next()) {
                    articles.add(newInstance(resultSet));
                }
                return articles;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про подібні статті до тієї, яка має article_id = " + articleId, e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про подібні статті до тієї, яка має article_id = " + articleId, e);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }

    private void appendFilteringQuery(final StringBuilder query, final Map<String, Object> positions, final String field, final String operationShortName, final Object value) {
        if (positions.size() == 0) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
        query.append(field);
        query.append(' ');
        query.append(FilterOperationMapperUtil.map(operationShortName));
        query.append(' ');
        query.append('?');
        positions.put(field, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Article> getAll(final FilteringArticle filteringArticle) {
        try (Connection connection = connectionDAO.getConnection()) {
            // побудова запиту із можливими різноманітними налаштуваннями фільтрації
            StringBuilder query = new StringBuilder();
            query.append(QUERY_GET_ALL_WITH_PREVIEW);
            Map<String, Object> positions = new LinkedHashMap<>();

            Category category = filteringArticle.getCategory();
            String categoryOperation = filteringArticle.getCategoryOperation();
            if (category != null && categoryOperation != null && !categoryOperation.isEmpty()) {
                appendFilteringQuery(query, positions, "articles.category_id", categoryOperation, category.getId());
            }

            User user = filteringArticle.getUser();
            String userOperation = filteringArticle.getUserOperation();
            if (user != null && userOperation != null && !userOperation.isEmpty()) {
                appendFilteringQuery(query, positions, "articles.user_id", userOperation, user.getId());
            }

            String name = filteringArticle.getName();
            String nameOperation = filteringArticle.getNameOperation();
            if (name != null && !name.isEmpty() && nameOperation != null && !nameOperation.isEmpty()) {
                appendFilteringQuery(query, positions, "articles.name", nameOperation, name);
            }

            String content = filteringArticle.getContent();
            String contentOperation = filteringArticle.getContentOperation();
            if (content != null && !content.isEmpty() && contentOperation != null && !contentOperation.isEmpty()) {
                appendFilteringQuery(query, positions, "content"/*не потребує префіксу, бо є аліасом*/, contentOperation, content);
            }

            Date modificationDate = filteringArticle.getModificationDate();
            String modificationDateOperation = filteringArticle.getModificationDateOperation();
            if (modificationDate != null && modificationDateOperation != null && !modificationDateOperation.isEmpty()) {
                if (positions.size() == 0) {
                    query.append(" WHERE ");
                } else {
                    query.append(" AND ");
                }
                query.append("articles.modification_date ");
                query.append(FilterOperationMapperUtil.map(modificationDateOperation));
                query.append(" TO_DATE(?, 'yyyy-MM-dd')");
                positions.put("articles.modification_date", modificationDate);
            }

            Integer views = filteringArticle.getViews();
            String viewsOperation = filteringArticle.getViewsOperation();
            if (views != null && viewsOperation != null && !viewsOperation.isEmpty()) {
                appendFilteringQuery(query, positions, "articles.views", viewsOperation, views);
            }

            // сортування
            String orderBy = filteringArticle.getOrderBy();
            String orderDirection = filteringArticle.getOrderDirection();
            if (orderBy != null && !orderBy.isEmpty() && orderDirection != null && !orderDirection.isEmpty()) {
                query.append(" ORDER BY ");
                // користувач не повинен працювати з ідентифікаторами - йому
                // краще надавати сортування за назвами
                if (orderBy.equals("category_id")) {
                    query.append("categories.name");
                } else if (orderBy.equals("user_id")) {
                    query.append("users.first_name");
                } else {
                    // лише content не потребує префіксу, бо виступає аліасом у запиті
                    if (!orderBy.equals("content")) {
                        query.append("articles.");
                    }
                    query.append(orderBy);
                }
                query.append(' ');
                query.append(orderDirection);
            }

            // пагінація
            Integer page = filteringArticle.getPage();
            Integer pageSize = filteringArticle.getPageSize();

            if (page != null && pageSize != null) {
                query.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
            }

            // отримання типів та встановлення їх значень
            PreparedStatement statement = connection.prepareStatement(query.toString());
            Set<String> keys = positions.keySet();
            int seq = 0;
            for (String key : keys) {
                if (key.equals("articles.category_id") || key.equals("articles.user_id") || key.equals("articles.views")) {
                    statement.setInt(++seq, (Integer) positions.get(key));
                } else if (key.equals("articles.name")) {
                    if (nameOperation.equals("starts")) {
                        statement.setString(++seq, positions.get(key) + "%");
                    } else if (nameOperation.equals("ends")) {
                        statement.setString(++seq, "%" + positions.get(key));
                    } else if (nameOperation.equals("contains")) {
                        statement.setString(++seq, "%" + positions.get(key) + "%");
                    } else {
                        statement.setString(++seq, (String) positions.get(key));
                    }
                } else if (key.equals("content") /*аліас*/) {
                    if (contentOperation.equals("starts")) {
                        statement.setString(++seq, positions.get(key) + "%");
                    } else if (contentOperation.equals("ends")) {
                        statement.setString(++seq, "%" + positions.get(key));
                    } else if (contentOperation.equals("contains")) {
                        statement.setString(++seq, "%" + positions.get(key) + "%");
                    } else {
                        statement.setString(++seq, (String) positions.get(key));
                    }
                } else if (key.equals("articles.modification_date")) {
                    statement.setString(++seq, ((Date) positions.get(key)).toString());
                }
            }

            if (page != null && pageSize != null) {
                statement.setInt(++seq, (page - 1) * pageSize);
                statement.setInt(++seq, pageSize);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Article> articles = new LinkedList<>();
                while (resultSet.next()) {
                    articles.add(newInstance(resultSet));
                }
                return articles;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про всі статті, що відповідають фільтру", e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про всі статті, що відповідають фільтру", e);
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
    public int getAllAmount(final FilteringArticle filteringArticle) {
        // щоб отримати кількість усіх статей
        filteringArticle.setPage(null);
        filteringArticle.setPageSize(null);
        return getAll(filteringArticle).size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLastCommentLocalId(final int articleId) {
        try (Connection connection = connectionDAO.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_LAST_COMMENT_LOCAL_ID);
            statement.setInt(1, articleId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("local_id");
                }
                return 0;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на отримання даних про local_id останнього коментаря до статті, яка має article_id = " + articleId, e);
                throw new PersistException("Не вдалося виконати запит на отримання даних про local_id останнього коментаря до статті, яка має article_id = " + articleId, e);
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
    public boolean increaseViews(final int articleId) {
        try (Connection connection = connectionDAO.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_INCREASE_VIEWS)) {
                statement.setInt(1, articleId);

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Не вдалося виконати запит на оновлення переглядів статті, у якої article_id = " + articleId, e);
                throw new PersistException("Не вдалося виконати запит на оновлення переглядів статті, у якої article_id = " + articleId, e);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }
}

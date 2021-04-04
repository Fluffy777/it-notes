package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.AbstractDAO;
import com.fluffy.spring.daos.CategoryDAO;
import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.UserDAO;
import com.fluffy.spring.domain.Article;
import com.fluffy.spring.domain.Category;
import com.fluffy.spring.domain.User;
import com.fluffy.spring.exceptions.PersistException;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleArticleDAO extends AbstractDAO<Article, Integer> {
    private final CategoryDAO categoryDAO;
    private final UserDAO userDAO;

    private static class PersistArticle extends Article {
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    public OracleArticleDAO(ConnectionDAO connectionDAO, CategoryDAO categoryDAO, UserDAO userDAO) {
        super(connectionDAO);
        this.categoryDAO = categoryDAO;
        this.userDAO = userDAO;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO articles (category_id, user_id, name, content, modification_date) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT article_id, category_id, user_id, name, content, modification_date FROM articles";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE articles SET category_id = ?, user_id = ?, name = ?, content = ?, modification_date = ? WHERE article_id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM articles WHERE article_id = ?";
    }

    @Override
    protected List<Article> parseResultSet(ResultSet resultSet) throws PersistException {
        List<Article> result = new LinkedList<>();
        try {
            while (resultSet.next()) {
                PersistArticle article = new PersistArticle();
                article.setId(resultSet.getInt("article_id"));
                article.setCategory((Category) categoryDAO.getByPK(resultSet.getInt("category_id")));
                article.setUser((User) userDAO.getByPK(resultSet.getInt("user_id")));
                article.setName(resultSet.getString("name"));
                article.setContent(resultSet.getString("content"));
                article.setModificationDate(resultSet.getDate("modification_date"));

                result.add(article);
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return result;
    }

    private void prepareStatement0(PreparedStatement statement, Article object) throws SQLException {
        statement.setInt(1, object.getCategory().getId());
        statement.setInt(2, object.getUser().getId());
        statement.setString(3, object.getName());
        statement.setString(4, object.getContent());
        statement.setDate(5, object.getModificationDate());
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Article object) throws PersistException {
        try {
            prepareStatement0(statement, object);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Article object) throws PersistException {
        try {
            prepareStatement0(statement, object);
            statement.setInt(6, object.getId());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }
}

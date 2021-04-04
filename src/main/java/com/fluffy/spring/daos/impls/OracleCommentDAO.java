package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.*;
import com.fluffy.spring.domain.Article;
import com.fluffy.spring.domain.Comment;
import com.fluffy.spring.domain.User;
import com.fluffy.spring.exceptions.PersistException;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleCommentDAO extends AbstractDAO<Comment, Integer> {
    private final ArticleDAO articleDAO;
    private final CommentDAO commentDAO;
    private final UserDAO userDAO;

    private static class PersistComment extends Comment {
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    public OracleCommentDAO(ConnectionDAO connectionDAO, ArticleDAO articleDAO, CommentDAO commentDAO, UserDAO userDAO) {
        super(connectionDAO);
        this.articleDAO = articleDAO;
        this.commentDAO = commentDAO;
        this.userDAO = userDAO;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO comments (article_id, parent_id, user_id, content, timestamp) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT comment_id, article_id, parent_id, user_id, content, timestamp FROM comments";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE comments SET article_id = ?, parent_id = ?, user_id = ?, content = ?, timestamp = ? WHERE comment_id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM comments WHERE comment_id = ?";
    }

    @Override
    protected List<Comment> parseResultSet(ResultSet resultSet) throws PersistException {
        List<Comment> result = new LinkedList<>();
        try {
            while (resultSet.next()) {
                PersistComment comment = new PersistComment();
                comment.setId(resultSet.getInt("comment_id"));
                comment.setArticle((Article) articleDAO.getByPK(resultSet.getInt("article_id")));
                comment.setParent((Comment) commentDAO.getByPK(resultSet.getInt("parent_id")));
                comment.setUser((User) userDAO.getByPK(resultSet.getInt("user_id")));
                comment.setContent(resultSet.getString("content"));
                comment.setTimestamp(resultSet.getTimestamp("timestamp"));

                result.add(comment);
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return result;
    }

    private void prepareStatement0(PreparedStatement statement, Comment object) throws SQLException {
        statement.setInt(1, object.getArticle().getId());
        statement.setInt(2, object.getParent().getId());
        statement.setInt(3, object.getUser().getId());
        statement.setString(4, object.getContent());
        statement.setTimestamp(5, object.getTimestamp());
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Comment object) throws PersistException {
        try {
            prepareStatement0(statement, object);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Comment object) throws PersistException {
        try {
            prepareStatement0(statement, object);
            statement.setInt(6, object.getId());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }
}

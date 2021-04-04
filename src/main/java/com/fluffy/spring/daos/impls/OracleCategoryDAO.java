package com.fluffy.spring.daos.impls;

import com.fluffy.spring.daos.AbstractDAO;
import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.domain.Category;
import com.fluffy.spring.exceptions.PersistException;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleCategoryDAO extends AbstractDAO<Category, Integer> {
    private static class PersistCategory extends Category {
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    public OracleCategoryDAO(ConnectionDAO connectionDAO) {
        super(connectionDAO);
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO categories (name) VALUES (?)";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT category_id, name FROM categories";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE categories SET name = ? WHERE category_id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM categories WHERE category_id = ?";
    }

    @Override
    protected List<Category> parseResultSet(ResultSet resultSet) throws PersistException {
        List<Category> result = new LinkedList<>();
        try {
            while (resultSet.next()) {
                PersistCategory category = new PersistCategory();
                category.setId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("name"));

                result.add(category);
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return result;
    }

    private void prepareStatement0(PreparedStatement statement, Category object) throws SQLException {
        statement.setString(1, object.getName());
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Category object) throws PersistException {
        try {
            prepareStatement0(statement, object);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Category object) throws PersistException {
        try {
            prepareStatement0(statement, object);
            statement.setInt(2, object.getId());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }
}

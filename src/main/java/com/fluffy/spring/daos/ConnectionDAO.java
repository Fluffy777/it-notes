package com.fluffy.spring.daos;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDAO {
    private final DataSource dataSource;

    public ConnectionDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        try {
            if (dataSource == null) {
                throw new IllegalStateException("dataSource == null");
            }
            return dataSource.getConnection();
        } catch (SQLException e) {
            // потребує обгортання
            throw e;
        }
    }
}

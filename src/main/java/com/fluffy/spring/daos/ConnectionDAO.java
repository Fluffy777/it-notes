package com.fluffy.spring.daos;

import com.fluffy.spring.daos.exceptions.DBConnectionException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Клас біна, що інкапсулює джерело даних, для якого, під час звернення,
 * створюється нове з'єднання або використовується наявне із пулу.
 * @author Сивоконь Вадим
 */
@Component
public class ConnectionDAO {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(ConnectionDAO.class);

    /**
     * Джерело даних.
     */
    private final DataSource dataSource;

    /**
     * Створює бін для роботи із джерелом даних.
     * @param dataSource джерело даних
     */
    public ConnectionDAO(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Повертає нове або існуюче з'єднання (усе в залежності від того, як
     * налаштований пул з'єднань, і чи він узагалі наявний).
     * @return з'єднання із джерелом даних
     * @throws SQLException якщо не вдалося отримати нове з'єднання
     */
    public Connection getConnection() throws SQLException {
        try {
            if (dataSource == null) {
                logger.error("dataSource == null");
                throw new IllegalStateException("dataSource == null");
            }
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
            throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
        }
    }
}

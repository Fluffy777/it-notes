package com.fluffy.spring.listeners;

import com.fluffy.spring.daos.ConnectionDAO;
import com.fluffy.spring.daos.exceptions.DBConnectionException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptStatementFailedException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Клас біна, що буде слугувати слухачем моменту запуску веб-додатку. Це
 * дозволяє виконати додаткову логіку під час завантаження.
 */
@Component
public class StartupListener
        implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(StartupListener.class);

    /**
     * Статична змінна, що зберігає стан - чи була виконана логіка
     * ініціалізації. static застосовується, оскільки бін створюється
     * декілька разів, тому треба додатово зберігати стан.
     */
    private static boolean startupHandled = false;

    /**
     * Бін, необхідний для підключення до бази даних.
     */
    private final ConnectionDAO connectionDAO;

    /**
     * Ресурс, що містить скрипт для ініціалізації таблиць у базі даних.
     */
    @Value("classpath:sql/tables.sql")
    private Resource tables;

    /**
     * Ресурс, що містить скрипт для заповнення таблиць у базі даних.
     */
    @Value("classpath:sql/data.sql")
    private Resource data;

    /**
     * Створює бін слухача.
     * @param connectionDAO бін, необхідний для підключення до бази даних
     */
    public StartupListener(final ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    /**
     * Метод, що містить логіку реагування на подію, що прослуховується.
     * @param event подія
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!startupHandled) {
            // механізм забезпечення одногоразового виконання
            startupHandled = true;
            logger.info("Ініціалізація додатку почала відбуватися");

            try (Connection connection = connectionDAO.getConnection()) {
                try {
                    ScriptUtils.executeSqlScript(connection, tables);
                    ScriptUtils.executeSqlScript(connection, data);
                } catch (ScriptStatementFailedException e) {
                    logger.info("Дані вже наявні - база даних ініціалізована");
                }
            } catch (SQLException e) {
                // не вдалося встановити з'єднання
                logger.log(Level.ERROR, "Не вдалося отримати з'єднання із базою даних", e);
                throw new DBConnectionException("Не вдалося отримати з'єднання із базою даних", e);
            }
        }
    }
}

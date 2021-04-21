package com.fluffy.spring.controllers.handlers;

import com.fluffy.spring.daos.exceptions.DBConnectionException;
import com.fluffy.spring.daos.exceptions.PersistException;
import com.fluffy.spring.util.ErrorPageUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Клас, призначений для обробки винятків.
 * @author Сивоконь Вадим
 */
@ControllerAdvice
public class PrimaryExceptionHandler {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(PrimaryExceptionHandler.class);

    /**
     * Обробник виняткової ситуації, що виникає, коли сторінка, на яку
     * намагається перейти користувач, не знайдена.
     * @param ex виняток
     * @return модель (параметри) та представлення
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNoHandlerFoundException(final NoHandlerFoundException ex) {
        logger.info("Помилка 404 оброблена (" + ex.getMessage() + ")");
        return new ModelAndView("errors/errorPage", "params", ErrorPageUtil.generateParams(
                "Помилка 404",
                "Не вдалося знайти цю сторінку :(",
                ErrorPageUtil.getExceptionExplanation(ex),
                "page-not-found.png",
                "Помилка 404"));
    }

    /**
     * Обробник виняткової ситуації, що виникає, коли не вдалося отримати
     * з'єднання із базою даних.
     * @param ex виняток
     * @return модель (параметри) та представлення
     */
    @ExceptionHandler(DBConnectionException.class)
    public ModelAndView handleDBConnectionException(final DBConnectionException ex) {
        logger.info("Помилка з'єднання оброблена (" + ex.getMessage() + ")");
        return new ModelAndView("errors/errorPage", "params", ErrorPageUtil.generateParams(
                "Помилка з'єднання",
                "Не вдалося встановити з'єднання із базою даних",
                ErrorPageUtil.getExceptionExplanation(ex),
                "database-error.png",
                "Помилка з'єднання"));
    }

    /**
     * Обробник виняткової ситуації, що виникає, коли не вдалося виконати
     * операцію у базі даних.
     * @param ex виняток
     * @return модель (параметри) та представлення
     */
    @ExceptionHandler(PersistException.class)
    public ModelAndView handlePersistException(final PersistException ex) {
        logger.info("Помилка бази даних оброблена (" + ex.getMessage() + ")");
        return new ModelAndView("errors/errorPage", "params", ErrorPageUtil.generateParams(
                "Помилка бази даних",
                "Сталася помилка бази даних",
                ErrorPageUtil.getExceptionExplanation(ex),
                "database-warning.png",
                "Помилка бази даних"));
    }

    /**
     * Обробник непередбачуваної виняткової ситуації.
     * @param ex виняток
     * @return модель (параметри) та представлення
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleUnknownException(final Exception ex) {
        logger.info("Невідома помилка оброблена (" + ex.getMessage() + ")");
        return new ModelAndView("errors/errorPage", "params", ErrorPageUtil.generateParams(
                "Невідома помилка",
                "Сталася не відома нам помилка",
                ErrorPageUtil.getExceptionExplanation(ex),
                "unknown-issue.png",
                "Невідома помилка"));
    }
}

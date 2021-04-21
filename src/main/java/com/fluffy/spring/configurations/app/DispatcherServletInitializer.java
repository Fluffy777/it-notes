package com.fluffy.spring.configurations.app;

import com.fluffy.spring.configurations.security.SecurityConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletRegistration;

/**
 * Клас-ініціалізатор для DispatcherServlet. Необхідний для впровадження
 * його програмної конфігурації - без використання файлу web.xml.
 * @author Сивоконь Вадим
 */
public class DispatcherServletInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * Вказує конфігураційні класи та біни для Root Application Context.
     * @return конфігураційні класи та біни для Root Application Context
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {SecurityConfiguration.class};
    }

    /**
     * Вказує конфігураційні класи та біни для сервлета.
     * @return конфігураційні класи та біни для сервлета
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {ApplicationConfiguration.class};
    }

    /**
     * Задає множину URL, які обробляються даним сервлетом. / - будь-які шляхи.
     * @return множина URL-шляхів, що обробляються сервлетом
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    /**
     * Задає додаткові налаштування для процесу реєстрації сервлета.
     * @param registration об'єкт для налаштування параметрів реєстрації сервлета
     */
    @Override
    protected void customizeRegistration(final ServletRegistration.Dynamic registration) {
        // прибирає обробник виняткової ситуації, що виникає, коли сторінка не
        // знайдена, для того, щоб її міг обробити PrimaryExceptionHandler, що
        // відмічений аннотацією @ControllerAdvice
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}

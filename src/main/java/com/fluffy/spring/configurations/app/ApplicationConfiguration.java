package com.fluffy.spring.configurations.app;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.nio.file.Paths;
import java.util.Hashtable;

/**
 * Клас, що відповідає за конфігурацію основних бінів, що використовуються
 * в додатку.
 * @author Сивоконь Вадим
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.fluffy.spring")
@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource("classpath:messages.properties"),
})
public class ApplicationConfiguration implements WebMvcConfigurer {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(ApplicationConfiguration.class);

    /**
     * Бін для отримання значень властивостей.
     */
    private final Environment env;

    /**
     * Створює об'єкт класу конфігурації.
     * @param env бін для отримання значень властивостей
     */
    public ApplicationConfiguration(final Environment env) {
        this.env = env;
    }

    /**
     * Створює бін, що відповідає за отримання JSP-сторінок за відповідними
     * іменами.
     * @return бін, що встановлює відповідність між JSP-сторінками і їх іменами
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * Додає додаткові обробники ресурсів, які дозволять читати файли із
     * відповідних директорій.
     * @param registry об'єкт, що зберігає зареєстровані обробники ресурсів
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

        registry
                .addResourceHandler("/icons/**")
                .addResourceLocations("file:/"
                        + Paths.get("icons").toFile().getAbsolutePath() + "/");
    }

    /**
     * Створює бін джерела даних.
     * @param configurationType тип конфігурації (DRIVER_MANAGER або JNDI)
     * @return бін джерела даних
     */
    @Bean(destroyMethod = "")
    public DataSource dataSource(
            @Value("${application.data-source.configuration-type}") final String configurationType) {
        if (configurationType.equals(env.getProperty("application.data-source.configuration-type-option.driver-manager"))) {
            // тип конфігурації DRIVER_MANAGER: отримання джереа ресурсів
            // покладається на DriverManager, пул підключень не забезпечується,
            // тому такий варіант конфігурації вважається небажаним
            logger.debug("Для стврення DataSource використовується DRIVER_MANAGER-конфігурація");

            DriverManagerDataSource dataSource = new DriverManagerDataSource(
                    env.getProperty("application.driver-manager.data-source.connection-url"),
                    env.getProperty("application.driver-manager.data-source.user"),
                    env.getProperty("application.driver-manager.data-source.password")
            );
            dataSource.setDriverClassName(env.getProperty("application.driver-manager.data-source.driver-class"));
            return dataSource;
        } else if (configurationType.equals(env.getProperty("application.data-source.configuration-type-option.jndi"))) {
            // тип конфігурації JNDI: бін джерела даних буде створений, із
            // використанням визначеного на WebLogic сервері спеціального
            // сервісу, забезпечується пул підключень, завдяки чому
            // відбуватиметься використання - більш ефективна конфігурація
            logger.debug("Для створення DataSource використовується JNDI-конфігурація");

            try {
                // початкові налаштування контексту для здійснення пошуку
                // сервісу
                Hashtable<String, String> contextEnv = new Hashtable<>();
                contextEnv.put(Context.INITIAL_CONTEXT_FACTORY, env.getProperty("application.jndi.initial-context-factory"));
                contextEnv.put(Context.PROVIDER_URL, env.getProperty("application.jndi.provider-url"));

                // пошук сервісу, що забезпечить створення біна DataSource
                Context context = new InitialContext(contextEnv);
                Object lookup = context.lookup(env.getProperty("application.jndi.data-source.lookup-service"));
                if (lookup != null) {
                    return (DataSource) lookup;
                } else {
                    logger.error("Не вдалося знайти сервіс JNDI");
                    throw new RuntimeException("Не вдалося знайти сервіс JNDI");
                }
            } catch (NamingException e) {
                logger.log(Level.ERROR, "Помилка іменування шуканого JNDI сервісу", e);
                throw new RuntimeException("Помилка іменування шуканого сервісу", e);
            }
        }
        logger.warn("Повернувся dataSource == null");
        return null;
    }

    /**
     * Створює бін, що займається шифруванням паролів за алгоритмом BCrypt.
     * Має силу шифрування (10 - за замовчуванням).
     * Описується саме в цьому конфігураційному класі (а не в пакеті security),
     * щоб розв'язати циклічні залежності між SecurityConfiguration та
     * AuthenticationProviderImpl.
     * @return бін для шифрування паролів за алгоритмом BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Створює бін, який визначає розміщення файлу із текстами помилок, що
     * відображаються після непроходження серверної валідації.
     * @return бін, що визначає розміщення файлу із текстами помилок для
     *         серверної валідації
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Створює бін, який визначає конфігурацію підтримки передачі файлів через
     * форми.
     * @return бін для конфігурування підтримки передачі файлів через форми
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(Integer.parseInt(env.getProperty("application.icons-max-upload-size")));
        return multipartResolver;
    }
}

package com.fluffy.spring.configurations.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.fluffy.spring")
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration implements WebMvcConfigurer {
    private final Environment env;

    public ApplicationConfiguration(Environment env) {
        this.env = env;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public DataSource getDataSource(
            @Value("${application.data-source.configuration-type}") String configurationType) {
        if (configurationType.equals(env.getProperty("application.data-source.configuration-type-option.driver-manager"))) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource(
                    env.getProperty("application.driver-manager.data-source.connection-url"),
                    env.getProperty("application.driver-manager.data-source.user"),
                    env.getProperty("application.driver-manager.data-source.password")
            );
            dataSource.setDriverClassName(env.getProperty("application.driver-manager.data-source.driver-class"));
            return dataSource;
        } else if (configurationType.equals(env.getProperty("application.data-source.configuration-type-option.jndi"))) {
            try {
                Context context = new InitialContext();
                Object lookup = context.lookup(env.getProperty("application.jndi.data-source.lookup-service"));
                if (lookup != null) {
                    return (DataSource) lookup;
                } else {
                    // TODO: опис виняткової ситуації
                    throw new RuntimeException("");
                }
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

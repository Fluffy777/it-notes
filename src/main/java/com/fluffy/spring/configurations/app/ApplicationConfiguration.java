package com.fluffy.spring.configurations.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.fluffy.spring")
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration implements WebMvcConfigurer {
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public DataSource getDataSource(@Value("${application.data-source.source-name}") String sourceName,
                                    @Value("${application.data-source.connection-url}") String connectionUrl,
                                    @Value("${application.data-source.driver-class}") String driverClass,
                                    @Value("${application.data-source.user}") String user,
                                    @Value("${application.data-source.password}") String password) {

        return null;
    }
}

package com.fluffy.spring.configurations.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Клас, що відповідає за конфігурацію аспектно-орієнтованого програмування.
 * @author Сивоконь Вадим
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.fluffy.spring")
public class AOPConfiguration {
    /**
     * Створює об'єкт класу конфігурації.
     */
    public AOPConfiguration() { }
}

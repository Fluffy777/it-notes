package com.fluffy.spring.configurations.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Клас конфігурації, що налаштовує Spring Security.
 * @author Сивоконь Вадим
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "com.fluffy.spring")
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:messages.properties")
})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(SecurityConfiguration.class);

    /**
     * Провайдер аутентифікації, що буде використовуватися.
     */
    @Autowired
    private AuthenticationProvider authenticationProvider;

    /**
     * Створює об'єкт конфігураційного класу.
     */
    public SecurityConfiguration() { }

    /**
     * Встановлює, який саме провайдер аутентифікації буде використовуватися.
     * @param auth провайдер аутентифікації
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
        logger.info("Провайдер аутентифікації встановлений");
    }

    /**
     * Конфігурує доступ за різними URL-адресами.
     * @param http HTTP-запит
     * @throws Exception якщо сталася помилка під час конфігурування
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login", "/signup").anonymous()
                .antMatchers("/profile", "/logout").authenticated()
                .antMatchers(
                        "/articles/create",
                        "/articles/edit",
                        "/categories/create",
                        "/categories/edit",
                        "/categories/delete",
                        "/users/delete").hasRole("ADMIN")
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/processing")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
            .and()
                .csrf()
                .disable();
        logger.info("Доступ за URL-адресами сконфігурований");
    }

    /**
     * Конфігурує, за якими URL налаштування захисту будуть ігноруватися.
     * @param web об'єкт WebSecurity
     */
    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
        logger.info("Ігнорування налаштувань захисту для папки з ресурсами налаштоване");
    }
}

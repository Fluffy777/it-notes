package com.fluffy.spring.configurations.security;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Клас сервісу, що призначений для можливості завантаження даних про окремого
 * користувача (у вигляді UserDetails) за його ім'ям (що в нашому випадку -
 * email).
 * @author Сивоконь Вадим
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

    /**
     * Сервіс для отримання даних про користувачів.
     */
    private final UserService userService;

    /**
     * Створює об'єкт (бін) сервісу.
     * @param userService сервіс для отримання даних про користувачів
     */
    public UserDetailsServiceImpl(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Повертає дані користувача за його іменем (у нашому випадку - email).
     * @param email ім'я користувача
     * @return дані про користувача
     * @throws UsernameNotFoundException якщо користувач із таким іменем не був
     *         знайдений
     */
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if (user != null) {
            logger.info("Користувач із email = " + email + " був знайдений, його дані для аутентифікації сформовані");
            return new UserDetailsImpl(user);
        } else {
            logger.warn("Не вдалося сформувати UserDetails для користувача, у якого email = " + email);
            throw new UsernameNotFoundException("Не вдалося сформувати UserDetails для користувача, у якого email = " + email);
        }
    }
}

package com.fluffy.spring.configurations.security;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Клас провайдера аутентифікації, що підтримує можливість її здійснення.
 * @author Сивоконь Вадим
 */
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(AuthenticationProviderImpl.class);

    /**
     * Сервіс, що займається отриманням даних про окремого користувача.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Бін, що займається шифруванням паролів.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Створює бін провайдера аутентифікації.
     * @param userDetailsService сервіс, що займається отриманням даних
     *                           про окремого користувача
     * @param passwordEncoder бін, що займається шифруванням паролів
     */
    public AuthenticationProviderImpl(final UserDetailsService userDetailsService,
                                      final PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Надає користувачу можливість аутентифікуватися.
     * @param authentication об'єкт запиту на проведення аутентифікації
     * @return об'єкт аутентифікації, що вдало її пройшов, у результаті чого
     *         містить ідентифікаційні дані, такі як userDetails (за email'ом)
     *         та пароль
     * @throws AuthenticationException якщо аутентифікація завершилася помилкою
     */
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.info("Аутентифікація користувача " + email + " прошла успішно");
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        } else {
            logger.warn("Неправильний пароль під час аутентифікації користувача " + email);
            throw new BadCredentialsException("Неправильний пароль");
        }
    }

    /**
     * Перевіряє, чи підтримується даний об'єкт запиту на проведення
     * аутентифікації.
     * @param aClass клас об'єкта запиту на аутентифікації
     * @return наявна підтримка або ні
     */
    @Override
    public boolean supports(final Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

package com.fluffy.spring.configurations.security;

import com.fluffy.spring.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Клас об'єкта даних про користувача, що використовуються в процесі
 * аутентифікації.
 * @author Сивоконь Вадим
 */
public class UserDetailsImpl implements UserDetails {
    /**
     * Префікс, що використовується для іменування наданих користувачу прав
     * таким чином, щоб вони вважалися ролями.
     */
    private static final String ROLE_PREFIX = "ROLE_";

    /**
     * Модель користувача, що може зберігатися в базі даних.
     */
    private final User user;

    /**
     * Створює об'єкт даних про користувача на основі його моделі в базі даних.
     * @param user модель користувача
     */
    public UserDetailsImpl(final User user) {
        this.user = user;
    }

    /**
     * Призначає користувачу права (ролі).
     * @return колекція, що містить права (ролі) користувача
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX
                        + role.getName()))
                .collect(Collectors.toSet());
    }

    /**
     * Повертає пароль користувача.
     * @return пароль користувача
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Повертає ім'я користувача.
     * @return ім'я користувача (у нашому випадку - email)
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Повертає, чи є аккаунт незалежним від часу.
     * @return чи є аккаунт незалежним від часу
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Повертає, чи є аккаунт незаблокованим.
     * @return чи є аккаунт незаблокованим
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Повертає, чи є ідентифікаційні дані (пароль) незалежними від часу.
     * @return чи є ідентифікаційні дані незалежними від часу
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Повертає, чи є аккаунт доступним.
     * @return чи є аккаунт доступним
     */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    /**
     * Повертає модель користувача, якому відповідає об'єкт його даних.
     * @return модель користувача
     */
    public User getUser() {
        return user;
    }
}

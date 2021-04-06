package com.fluffy.spring.configurations.security;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if (user != null) {
            return new UserDetailsImpl(user);
        } else {
            throw new UsernameNotFoundException("Не вдалося сформувати UserDetails для користувача, у якого email = " + email);
        }
    }
}

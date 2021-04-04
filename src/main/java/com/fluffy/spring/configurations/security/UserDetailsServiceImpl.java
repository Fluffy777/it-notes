package com.fluffy.spring.configurations.security;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    //private final UserService userService;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails userDetails;
        //Optional<User> user;

        return null;
    }
}

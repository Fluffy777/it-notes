package com.fluffy.spring.listeners;

import com.fluffy.spring.services.IconStorageService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    private final Environment env;
    private final IconStorageService iconStorageService;

    public StartupListener(Environment env, IconStorageService iconStorageService) {
        this.iconStorageService = iconStorageService;
        this.env = env;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        iconStorageService.init();
    }
}

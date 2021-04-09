package com.fluffy.spring.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    private final Environment env;

    public StartupListener(Environment env) {
        this.env = env;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}

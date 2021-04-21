package com.fluffy.spring.configurations.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Клас аспекту логування, який визначає додаткові дії, що будуть виконані із
 * метою підтримки логування.
 * @author Сивоконь Вадим
 */
@Aspect
@Component
public class LoggerAspect {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(LoggerAspect.class);

    /**
     * Створює бін аспекту логування.
     */
    public LoggerAspect() {
    }

    /**
     * Додаткові дії, які будуть виконані до виконання endpoint'ів
     * контролерів, визначених у пакеті com.fluffy.spring.controllers та
     * підпакетах.
     * @param joinPoint момент у процесі виконання коду
     */
    @Before("within(com.fluffy.spring.controllers..*)")
    public void logBefore(final JoinPoint joinPoint) {
        logger.debug("Endpoint " + joinPoint.getSignature()
                + " був викликаний у контролера "
                + joinPoint.getSignature().getDeclaringTypeName());
    }

    /**
     * Додаткові дії, які будуть виконані після виконання endpoint'ів
     * контролерів, визначених у пакеті com.fluffy.spring.controllers та
     * підпакетах.
     * @param joinPoint момент у процесі виконання коду
     */
    @After("within(com.fluffy.spring.controllers..*)")
    public void logAfter(final JoinPoint joinPoint) {
        logger.debug("Endpoint " + joinPoint.getSignature() + " контролера "
                + joinPoint.getSignature().getDeclaringTypeName()
                + " завершив своє виконання");
    }
}

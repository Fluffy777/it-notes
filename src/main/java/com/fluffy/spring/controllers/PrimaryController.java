package com.fluffy.spring.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Клас основного контролера додатку, що керує відображенням основних сторінок.
 * @author Сивоконь Вадим
 */
@Controller
public class PrimaryController {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(PrimaryController.class);

    /**
     * Перехід на початкову сторінку.
     * @return назва початкової сторінки
     */
    @GetMapping("/")
    public String index() {
        logger.info("Здійснений перехід на основну сторінку");
        return "index";
    }
}

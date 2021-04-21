package com.fluffy.spring.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Клас контролера, що надає доступ до сторінок, призначених виключно для
 * адміністратора. Перехід на JSP-сторінки, назви яких повертаються,
 * відбувається за рахунок визначеного біна ViewResolver'а.
 * @author Сивоконь Вадим
 */
@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminUIController {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(AdminUIController.class);

    /**
     * Перехід на сторінку для створення статті.
     * @return назва сторінки для створення статті
     */
    @GetMapping("/articles/create")
    public String createArticleView() {
        logger.info("Здійснений перехід на сторінку створення статті");
        return "articles/create";
    }

    /**
     * Перехід на сторінку для редагування статті.
     * @param model модель для передачі додаткових даних
     * @param id ID статті
     * @return назва сторінки для редагування статті
     */
    @GetMapping("/articles/edit/{id}")
    public String editArticleView(final Model model, @PathVariable final int id) {
        model.addAttribute("articleId", id);
        logger.info("Здійснений перехід на сторінку редагування статті із id = " + id);
        return "articles/edit";
    }

    /**
     * Перехід на сторінку для створення категорії.
     * @return назва сторінки для створення категорії
     */
    @GetMapping("/categories/create")
    public String createCategoryView() {
        logger.info("Здійснений перехід на сторінку для створення категорії");
        return "categories/create";
    }

    /**
     * Перехід на сторінку для редагування категорії.
     * @return назва сторінки для редагування категорії
     */
    @GetMapping("/categories/edit")
    public String editCategoryView() {
        logger.info("Здійснений перехід на сторінку для редагування категорії");
        return "categories/edit";
    }

    /**
     * Перехід на сторінку для видалення категорії.
     * @return назва сторінки для видалення категорії
     */
    @GetMapping("/categories/delete")
    public String deleteCategoryView() {
        logger.info("Здійснений перехід на сторінку для видалення категорії");
        return "categories/delete";
    }

    /**
     * Перехід на сторінку для видалення користувача.
     * @return назва сторінки для видалення користувача
     */
    @GetMapping("/users/delete")
    public String deleteUserView() {
        logger.info("Здійснений перехід на сторінку для видалення користувача");
        return "users/delete";
    }
}

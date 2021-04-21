package com.fluffy.spring.controllers.rest;

import com.fluffy.spring.controllers.AuthController;
import com.fluffy.spring.domain.Article;
import com.fluffy.spring.domain.FilteringArticle;
import com.fluffy.spring.services.ArticleService;
import com.fluffy.spring.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Клас REST-контролера, що надає можливість маніпулювати статтями.
 * @author Сивоконь Вадим
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(ArticleController.class);

    /**
     * Сервіс для отримання даних про статті.
     */
    private final ArticleService articleService;

    /**
     * Сервіс для отримання даних про користувачів.
     */
    private final UserService userService;

    /**
     * Створює об'єкт контролера.
     * @param articleService сервіс для отримання даних про статті
     * @param userService сервіс для отримання даних про користувачів
     */
    public ArticleController(final ArticleService articleService,
                             final UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    /**
     * Повертає статтю за ID.
     * @param id ID статті
     * @return стаття
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable final int id) {
        Article article = articleService.findById(id);
        if (article != null) {
            logger.info("Були надані дані про статтю із id = " + id);
            return new ResponseEntity<>(article, HttpStatus.OK);
        }
        logger.warn("Не вдалося надані дані про статтю із id = " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Повертає найбільш популярні статті.
     * @param topSize розмір топ-рейтингу
     * @return найбільш популярні статті
     */
    @GetMapping("/most-popular")
    public ResponseEntity<?> getMostPopularArticles(@RequestParam final int topSize) {
        logger.info("Надані дані про найбільш популярні статті (у кількості " + topSize + ")");
        return new ResponseEntity<>(articleService.findMostPopular(topSize), HttpStatus.OK);
    }

    /**
     * Повертає схожі за категорією статті.
     * @param id ID статті, для якої треба шукати схожі
     * @param maxCount максимальна кількість схожих статей
     * @return схожі статті
     */
    @GetMapping("/same-articles")
    public ResponseEntity<?> getSameArticles(@RequestParam final int id,
                                             @RequestParam final int maxCount) {
        logger.info("Надані дані про схожі статті до тої, у якої id = " + id + " (максимальна кількість = " + maxCount + ")");
        return new ResponseEntity<>(articleService.findSame(id, maxCount), HttpStatus.OK);
    }

    /**
     * Повертає статті, що відповідають встановленому фільтру.
     * @param filteringArticle фільтр для статей
     * @return статті, що відповідають фільтру
     */
    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> filterArticles(@RequestBody final FilteringArticle filteringArticle) {
        logger.info("Надані дані про всі статті із застосуванням фільтрації");
        return new ResponseEntity<>(articleService.findAll(filteringArticle), HttpStatus.OK);
    }

    /**
     * Повертає кількість статей, що відповідають встановленому фільтру.
     * @param filteringArticle фільтр для статей
     * @return статті, що відповідають фільтру
     */
    @PostMapping(value = "/filter-amount", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> filterArticlesForSize(@RequestBody final FilteringArticle filteringArticle) {
        logger.info("Надана загальна кількість статей, що були обрані шляхом фільтрації");
        return new ResponseEntity<>(articleService.findAllAmount(filteringArticle), HttpStatus.OK);
    }

    /**
     * Створює статтю, що відповідає переданому об'єкту. Користувач та дата
     * оновлення перевизначаються на боці сервера, щоб вказати, що створення
     * статті відбувається від поточного користувача і для узгодження часу із
     * серверним.
     * @param article модель статті (заповнена частково, ще не представлена в
     *                базі даних)
     * @return модель статті (повністю заповнена, представлен в базі даних)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createArticle(@RequestBody final Article article) {
        article.setUser(userService.findByEmail(AuthController.getCurrentUsername()));
        article.setModificationDate(new java.sql.Date(new Date().getTime()));
        Article createdArticle = articleService.create(article);
        logger.info("Нова стаття '" + article.getName() + "' була створена");
        return new ResponseEntity<>(createdArticle, HttpStatus.OK);
    }

    /**
     * Оновлює статтю, що відповідає переданому об'єкту. Дата оновлення
     * перевизначається на боці сервера - для її узгодження із серверним.
     * @param article модель статті (заповнена частково, представлена в базі
     *                даних)
     * @return оновлена модель статті (заповнена повністю, представлена в базі
     *         даних)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateArticle(@RequestBody final Article article) {
        article.setModificationDate(new java.sql.Date(new Date().getTime()));
        Article updatedArticle = articleService.update(article.getId(), article);
        logger.info("Стаття із id = " + article.getId() + " була оновлена");
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    /**
     * Оновлює статтю за вказаним ID. Дозволяє частково змінювати статтю без
     * необхідності бути авторизованим або мати роль адміністратора.
     * @param id ID статті
     * @return повідомлення-відповідь щодо успіху оновлення переглядів
     */
    @PutMapping(value = "/view", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewArticleById(@RequestBody final int id) {
        if (articleService.increaseViews(id)) {
            logger.info("Кількість переглядів статті, у якої id = " + id + " вдалося оновити");
            return new ResponseEntity<>("{\"message\": \"Кількість переглядів статті, у якої article_id = " + id + " була успішно оновлена\"}", HttpStatus.OK);
        }
        logger.warn("Не вдалося оновити кількість переглядів статті");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Видаляє статтю за вказаним ID.
     * @param id ID статті
     * @return повідомлення-відповідь щодо успіху видалення статті
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable final int id) {
        if (articleService.delete(id)) {
            logger.info("Стаття із id = " + id + " була видалена");
            return new ResponseEntity<>("Стаття, у якої article_id = " + id + " була видалена", HttpStatus.OK);
        }
        logger.warn("Не вдалося видалити статтю із id = " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

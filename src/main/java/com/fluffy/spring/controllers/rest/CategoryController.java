package com.fluffy.spring.controllers.rest;

import com.fluffy.spring.domain.Category;
import com.fluffy.spring.services.CategoryService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * Клас REST-контролер, що надає можливість маніпулювати категоріями.
 * @author Сивоконь Вадим
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(CategoryController.class);

    /**
     * Сервіс для отримання даних про категорії.
     */
    private final CategoryService categoryService;

    /**
     * Створює об'єкт контролера.
     * @param categoryService сервіс для отримання даних про категорії
     */
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Повертає всі категорії.
     * @return усі категорії
     */
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        logger.info("Надані дані про всі категорії");
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    /**
     * Повертає категорію за ID.
     * @param id ID категорії
     * @return категорія
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable final int id) {
        Category category = categoryService.findById(id);
        if (category != null) {
            logger.info("Надані дані про категорію із id = " + id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
        logger.warn("Не вдалося знайти даних про категорію із id = " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Створює статтю, що відповідає переданому об'єкту.
     * @param category модель категорії (заповнена повністю, ще не представлена
     *                 в базі даних)
     * @return модель категорії (заповнена повністю, представлена в базі даних)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCategory(@RequestBody final Category category) {
        Category createdCategory = categoryService.create(category);
        logger.info("Нова категорія '" + category.getName() +"' була створена");
        return new ResponseEntity<>(createdCategory, HttpStatus.OK);
    }

    /**
     * Оновлює категорію, що відповідає переданому об'єкту.
     * @param category модель категорії (заповнена повністю, представлена в
     *                 базі даних)
     * @return оновлена модель категорії (заповнена повністю, представлена в
     *         базі даних)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCategory(@RequestBody final Category category) {
        Category updatedCategory = categoryService.update(category.getId(), category);
        logger.info("Категорія із id = " + category.getId() + " була оновлена");
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    /**
     * Видаляє категорію за вказаним ID.
     * @param id ID категорії
     * @return повідомлення-відповідь щодо успіху видалення категорії
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable final int id) {
        if (categoryService.delete(id)) {
            logger.info("Категорія із id = " + id + " була видалена");
            return new ResponseEntity<>("Категорія, у якої category_id = " + id + " була видалена", HttpStatus.OK);
        }
        logger.info("Не вдалося видалити категорію із id = " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

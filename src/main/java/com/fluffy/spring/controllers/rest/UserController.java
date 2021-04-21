package com.fluffy.spring.controllers.rest;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Клас REST-контролер, що надає можливість маніпулювати користувачами.
 * @author Сивоконь Вадим
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(UserController.class);

    /**
     * Сервіс для отримання даних про користувачів.
     */
    private final UserService userService;

    /**
     * Створює об'єкт контролера.
     * @param userService сервіс для отримання даних про користувачів
     */
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Повертає всіх користувачів.
     * @return усі користувачі
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        logger.info("Надані дані про всіх користувачів");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /**
     * Повертає користувача за його ID.
     * @param id ID користувача
     * @return користувач
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable final int id) {
        User user = userService.findById(id);
        if (user != null) {
            logger.info("Надані дані про користувача, у якого id = " + id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        logger.warn("Не вдалося знайти даних про користувача, у якого id = " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Повертає користувачів, у яких роль має певну назву.
     * @param name назва ролі
     * @return користувачі, що мають роль із назвою
     */
    @GetMapping("/with-role-name")
    public ResponseEntity<?> getUsersWithRoleName(@RequestParam final String name) {
        logger.info("Надані дані про користувачів, у яких роль має назву '" + name + "'");
        return new ResponseEntity<>(userService.findAllWithRoleName(name), HttpStatus.OK);
    }

    /**
     * Створює користувача, що відповідає переданому об'єкту.
     * @param user модель користувача (заповнена повністю, не представлена в
     *             базі даних)
     * @return модель користувача (заповнена повністю, представлена в базі
     *         даних)
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody final User user) {
        User createdUser = userService.create(user);
        logger.info("Новий користувач " + user.getFirstName() + " " + user.getLastName() + " був створений");
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    /**
     * Оновлює користувача, що відповідає переданому об'єкту.
     * @param user модель користувач (заповнена повністю, не представлена в
     *             базі даних)
     * @return модель користувача заповнена повністю, представлена в базі даних()
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody final User user) {
        User updatedUser = userService.update(user.getId(), user);
        logger.info("Користувач із id = " + user.getId() + " був оновлений");
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Видаляє користувача за вказаним ID.
     * @param id ID користувача
     * @return повідомлення-відповідь щодо успіху видалення користувача
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable final int id) {
        if (userService.delete(id)) {
            logger.info("Користувач, у якого id = " + id + " був видалений");
            return new ResponseEntity<>("Користувач, у якого user_id = " + id + " був видалений", HttpStatus.OK);
        }
        logger.info("Не вдалося видалити користувача, у якого id = " + id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

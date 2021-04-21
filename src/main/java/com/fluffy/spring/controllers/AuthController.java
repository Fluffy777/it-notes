package com.fluffy.spring.controllers;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import com.fluffy.spring.validation.forms.LogInForm;
import com.fluffy.spring.validation.forms.SignUpForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Клас контролера, що відповідає за процес авторизації.
 * @author Сивоконь Вадим
 */
@Controller
public class AuthController {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(AuthController.class);

    /**
     * Валідатор даних, отриманих від форми авторизації.
     */
    private final Validator signUpFormValidator;

    /**
     * Сервіс для отримання даних про користувачів.
     */
    private final UserService userService;

    /**
     * Бін для шифрування паролів.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Створює об'єкт контролера.
     * @param signUpValidator валідатор для даних форми авторизації
     * @param userService сервіс для отримання даних про користувачів
     * @param passwordEncoder бін для шифрування паролів
     */
    public AuthController(@Qualifier("signUpFormValidator") final Validator signUpValidator,
                          final UserService userService,
                          final PasswordEncoder passwordEncoder) {
        this.signUpFormValidator = signUpValidator;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Визначає валідатор, що буде використовуватися для перевірки даних,
     * отриманих із форми.
     * @param binder об'єкт-зв'язувач
     */
    @InitBinder("signUpForm")
    protected void signUpFormInitBinder(final WebDataBinder binder) {
        binder.setValidator(signUpFormValidator);
        logger.info("Валідатор для даних форми реєстрації встановлений");
    }

    /**
     * Перехід на сторінку для авторизації.
     * @param error чи була знайдена помилка під час валідації
     * @param modelMap модель для передачі додаткових даних
     * @return назва сторінки для авторизації
     */
    @GetMapping("/login")
    public String logInView(@RequestParam(name = "error", required = false) final boolean error, final ModelMap modelMap) {
        modelMap.addAttribute("logInForm", new LogInForm());
        if (error) {
            logger.debug("Наявні помилки під час заповнення форми авторизації");
            modelMap.addAttribute("error", true);
        }
        return "/auth/login";
    }

    /**
     * Перехід на сторінку для реєстрації.
     * @param modelMap модель для передачі додаткових даних
     * @return назва сторінки для реєстрації
     */
    @GetMapping("/signup")
    public String signUpView(final ModelMap modelMap) {
        modelMap.addAttribute("signUpForm", new SignUpForm());
        logger.info("Здійснений перехід на сторінку реєстрації");
        return "/auth/signup";
    }

    /**
     * Метод, що опрацьовує процес реєстрації із врахуванням валідації.
     * @param signUpForm об'єкт, що містить дані із форми
     * @param result результат валідації
     * @return назва сторінки реєстрації (на випадок помилок валідації) або
     *         перехід на сторінку авторизації
     */
    @PostMapping("/signup")
    public String signUp(
            @Valid @ModelAttribute(name = "signUpForm") final SignUpForm signUpForm,
            final BindingResult result) {
        if (result.hasErrors()) {
            logger.debug("Наявні помилки під час заповнення реєстраційної форми");
            return "/auth/signup";
        }

        userService.create(signUpForm.buildUser(passwordEncoder));
        logger.info("Новий користувач успішно зареєстрований");
        return "redirect:/login";
    }

    /**
     * Метод дозволяє отримати поточного користувача, що здійснив вхід.
     * @return ім'я (у нашому випадку - email) користувача
     */
    public static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof User) {
            return ((User) principal).getEmail();
        } else {
            return principal.toString();
        }
    }
}

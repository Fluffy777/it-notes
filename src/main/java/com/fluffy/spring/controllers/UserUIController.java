package com.fluffy.spring.controllers;

import com.fluffy.spring.domain.User;
import com.fluffy.spring.services.UserService;
import com.fluffy.spring.util.FileUploadUtil;
import com.fluffy.spring.validation.forms.UserDataForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Клас контролера, що відповідає за функціонування користувацього інтерфейсу.
 * @author Сивоконь Вадим
 */
@Controller
public class UserUIController {
    /**
     * Для забезпечення логування.
     */
    private static final Logger logger = Logger.getLogger(UserUIController.class);

    /**
     * Валідатор даних, отриманих від форми редагування даних профілю.
     */
    private final Validator userDataFormValidator;

    /**
     * Сервіс для отримання даних про користувачів.
     */
    private final UserService userService;

    /**
     * Сервіс, що займається отриманням даних про окремого користувача.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Бін для шифрування паролів.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Створює об'єкт контролера.
     * @param userDataFormValidator валідатор для даних форми редагування даних
     *                              профілю
     * @param userService сервіс для отримання даних про користувачів
     * @param userDetailsService сервіс, що займається отриманням даних про окремого користувача
     * @param passwordEncoder бін для шифрування паролів
     */
    public UserUIController(@Qualifier("userDataFormValidator") final Validator userDataFormValidator,
                            final UserService userService,
                            final UserDetailsService userDetailsService,
                            final PasswordEncoder passwordEncoder) {
        this.userDataFormValidator = userDataFormValidator;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Визначає валідатор, що буде використовуватися для перевірки даних,
     * отриманих із форми.
     * @param binder об'єкт-зв'язувач
     */
    @InitBinder("userDataForm")
    protected void userDataFormInitBinder(final WebDataBinder binder) {
        binder.setValidator(userDataFormValidator);
        logger.info("Валідатор для даних форми редагування даних профілю встановлений");
    }

    /**
     * Перехід на сторінку профілю поточного користувача.
     * @param modelMap об'єкт для передачі додаткових даних
     * @return назва сторінки профілю поточного користувач
     */
    @GetMapping("/profile")
    public String profileView(final ModelMap modelMap) {
        User user = userService.findByEmail(AuthController.getCurrentUsername());
        modelMap.addAttribute("userDataForm", new UserDataForm());
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("iconsUploadPath", "icons");
        logger.info("Здійснений перехід на сторінку профілю поточного користувача (email = " + user.getEmail() + ")");
        return "profile";
    }

    /**
     * Оновлення даних профілю.
     * @param userDataForm об'єкт, що містить дані із форми
     * @param bindingResult результат валідації
     * @return назва сторінки, на якій будуть відображені зміни або помилки,
     *         виявлені під час валідації
     * @throws IOException якщо трапилася помилка під час опрацювання
     *         зображення профілю користувача
     */
    @PostMapping("/profile")
    public ModelAndView profileUpdate(
            @Valid @ModelAttribute(name = "userDataForm") final UserDataForm userDataForm,
            final BindingResult bindingResult
            ) throws IOException {
        // оновлені дані про поточного користувача (без userId, rday)
        User currentUser = userDataForm.buildUser(passwordEncoder);

        // описує стан поточного користувача у минулому
        User user = userService.findByEmail(AuthController.getCurrentUsername());

        // треба перенести деякі дані, щоб об'єкт currentUser міг представляти
        // поточного користувача
        currentUser.setId(user.getId());
        currentUser.setEnabled(user.isEnabled());
        currentUser.setRday(user.getRday());

        if (!bindingResult.hasErrors()) {
            // видалення останніх файлів іконок користувача, якщо відбувається
            // зміна іконки
            String newIcon = currentUser.getIcon();

            if (newIcon != null && !newIcon.isEmpty()) {
                String uploadDir = "icons/" + currentUser.getId();
                FileSystemUtils.deleteRecursively(Paths.get(uploadDir).toFile());

                // файл завантажується лише тоді, коли всі інші дані не містять
                // помилок - імітується "транзакція", її атомарність
                MultipartFile multipartFile = userDataForm.getIcon();
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                currentUser.setIcon(fileName);

                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

                logger.debug("Поточний користувач завантажив зображення профілю (email = " + currentUser.getEmail() + ")");
            } else {
                // завантаження іконки не відбувалося - можна використати попередню
                currentUser.setIcon(user.getIcon());
            }

            // під час проходження валідації помилок не виникало - треба
            // оновити дані про поточного користувача

            String password = currentUser.getPassword();
            String newPassword = userDataForm.getNewPassword();

            // новий пароль не порожній та помилок не було зафіксовано - отже
            // користувач міг змінити пароль
            if (newPassword != null && !newPassword.isEmpty()) {
                password = passwordEncoder.encode(newPassword);
                currentUser.setPassword(password);
            }

            // відповідні зміни в persistence-шарі веб-додатку
            userService.update(currentUser.getId(), currentUser);

            // користувач міг змінити дані, необхідні для проходження
            // авторизації, тому треба виконати додаткові налаштування
            String email = currentUser.getEmail();

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities()));

            logger.info("Дані поточного користувача були оновлені");
        } else {
            // поточному користувачу треба встановити іконку, що
            // використовувалася раніше, оскільки оновлення не відбулося
            // (для уникнення втрати відображення попередньої іконки)
            currentUser.setIcon(user.getIcon());
        }

        return new ModelAndView("profile", "user", currentUser);
    }

    /**
     * Перехід на сторінку для перегляду статті. Користувач матиме можливість
     * залишити коментар за умови проходження аутентифікації.
     * @param model об'єкт для передачі додаткових даних на сторінку
     * @param id ID статті
     * @return назва сторінки для відображення статті
     */
    @GetMapping("/articles/{id}")
    public String articleView(final Model model, @PathVariable final int id) {
        model.addAttribute("articleId", id);
        model.addAttribute("currentUser", userService.findByEmail(AuthController.getCurrentUsername()));
        logger.info("Здійснений перехід на сторінку для перегляду статті із id = " + id);
        return "articles/view";
    }
}

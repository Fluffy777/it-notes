package com.fluffy.spring.validation.validators;

import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Базовий клас для створення валідаторів. Містить основні константи - для
 * стандартизації вводу, а також методи, що можна використати для валідації.
 * @author Сивоконь Вадим
 */
public abstract class AbstractFormValidator implements Validator {
    /**
     * Для забезпечення логування.
     */
    protected final Logger logger = Logger.getLogger(getClass());

    /**
     * Бін для отримання значень властивостей.
     */
    protected final Environment env;

    /**
     * Ініціалізує поля, необхідні для функціонування валідатора, що базується
     * на абстрактному.
     * @param env бін для отримання значень властивостей
     */
    protected AbstractFormValidator(final Environment env) {
        this.env = env;
    }

    /**
     * Типи вводу.
     */
    public enum InputType {
        /**
         * Ім'я (як firstName, lastName).
         */
        NAME,

        /**
         * Пароль.
         */
        PASSWORD,

        /**
         * Електронна пошта.
         */
        EMAIL,

        /**
         * Дата.
         */
        DATE,

        /**
         * Гендер.
         */
        GENDER,

        /**
         * Доступність.
         */
        ENABLEABILITY,

        /**
         * Будь-який тип даних.
         */
        ANY;

        /**
         * Повертає рядкове представлення типу вводу як формату у вигляді
         * регулярного виразу.
         * @return регулярний вираз, що представляє формат введення
         */
        @Override
        public String toString() {
            switch (this) {
                case NAME:
                    return "^[A-ZА-Я][a-zа-яA-ZА-Я-]{1,48}[a-zа-яA-ZА-Я]$";
                case PASSWORD:
                    return "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
                case EMAIL:
                    return "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                case DATE:
                    return "^(?:(?:(?:0[1-9]|1\\d|2[0-8])\\/(?:0[1-9]|1[0-2])|(?:29|30)\\/(?:0[13-9]|1[0-2])|31\\/(?:0[13578]|1[02]))"
                            + "\\/[1-9]\\d{3}|29\\/02(?:\\/[1-9]\\d(?:0[48]|[2468][048]|[13579][26])|(?:[2468][048]|[13579][26])00))$";
                case GENDER:
                    return "^(male)|(female)$";
                case ENABLEABILITY:
                    return "^(enable)|(disable)$";
                case ANY:
                default:
                    return "^.*$";
            }
        }
    }

    /**
     * Виконує валідацію вхідних даних із врахуванням формату.
     * @param inputType тип вводу (визначає формат)
     * @param input дані, що необхідно провалідувати
     * @param errors об'єкт, що зберігає інформацію про зв'язування даних із
     *               елементами форми та помилки
     * @param field назва поля, що містить дані
     * @param errorCode назва властивості із текстом помилки
     */
    public void validate(final InputType inputType, final String input, final Errors errors, final String field, final String errorCode) {
        if (!input.matches(inputType.toString())) {
            errors.rejectValue(field, errorCode);
            logger.warn("Поле форми " + field + " не пройшло валідацію, код причини: " + errorCode);
        }
    }

    /**
     * Виконує валідацію вхідних даних із врахуванням їх довжини.
     * @param input дані, що необхідно провалідувати
     * @param length максимальна допустима довжина даних
     * @param errors об'єкт, що зберігає інформацію про зв'язування даних із
     *               елементами форми та помилки
     * @param field назва поля, що містить дані
     * @param errorCode назва властивості із текстом помилки
     */
    public void validateNullableStringByLength(final String input, final int length, final Errors errors, final String field, final String errorCode) {
        if (input != null && input.length() > length) {
            errors.rejectValue(field, errorCode);
            logger.warn("Поле форми " + field + " не пройшло валідацію, код причини: " + errorCode);
        }
    }
}

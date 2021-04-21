package com.fluffy.spring.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Допоміжний клас, що дозволяє більш зручно налаштовувати відображення
 * сторінки із помилкою.
 * @author Сивоконь Вадим
 */
public final class ErrorPageUtil {
    private ErrorPageUtil() { }

    /**
     * Генерує набір параметрів у вигляді словника.
     * @param header заголовок
     * @param message повідомлення
     * @param explanation пояснення (більш детальне)
     * @param img назва зображення
     * @param alt текст-альтернатива
     * @return словник як набір параметрів
     */
    public static Map<String, String> generateParams(final String header,
                                              final String message,
                                              final String explanation,
                                              final String img,
                                              final String alt) {
        Map<String, String> map = new HashMap<>();
        map.put("header", header);
        map.put("message", message);
        map.put("explanation", explanation);
        map.put("img", img);
        map.put("alt", alt);
        return map;
    }

    /**
     * Створює більш детальне пояснення винятку.
     * @param t виняток
     * @return більш детальне пояснення
     */
    public static String getExceptionExplanation(final Throwable t) {
        Throwable cause = t.getCause();
        if (cause != null) {
            String causeMessage = t.getCause().getMessage();
            if (causeMessage != null && !causeMessage.isEmpty()) {
                return t.getMessage() + " (" + causeMessage + ")";
            } else {
                return t.getMessage();
            }
        } else {
            return t.getMessage();
        }
    }
}

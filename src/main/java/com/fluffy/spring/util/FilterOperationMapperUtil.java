package com.fluffy.spring.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Допоміжний клас, призначений для перетворення скорочених назв операцій у їх
 * символьне представлення (знаки).
 * @author Сивоконь Вадим
 */
public final class FilterOperationMapperUtil {
    /**
     * Структура даних для збереження відповідностей між назвою операцій та їх
     * знаковими представленнями.
     */
    private static final Map<String, String> map;

    static {
        map = new HashMap<>();
        map.put("eq", "=");
        map.put("ne", "!=");
        map.put("lt", "<");
        map.put("gt", ">");
        map.put("starts", "LIKE");
        map.put("contains", "LIKE");
        map.put("ends", "LIKE");
    }

    private FilterOperationMapperUtil() { }

    /**
     * Надає знакове представлення операції, що має скорочену назву. Значення,
     * що повертається за замовчуванням - "=".
     * @param operationShortName коротка назва операції
     * @return відповідний знак операції
     */
    public static String map(final String operationShortName) {
        return map.getOrDefault(operationShortName, "=");
    }
}

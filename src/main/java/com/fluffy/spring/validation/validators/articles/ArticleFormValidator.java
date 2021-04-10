package com.fluffy.spring.validation.validators.articles;

import com.fluffy.spring.validation.forms.articles.ArticleForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ArticleFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ArticleForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // перевірка на непорожність
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "articleForm.name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "articleForm.category.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "articleForm.content.empty");

        System.out.println("validated");
    }
}

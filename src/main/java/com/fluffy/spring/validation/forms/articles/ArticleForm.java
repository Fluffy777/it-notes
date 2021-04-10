package com.fluffy.spring.validation.forms.articles;

import com.fluffy.spring.domain.Article;
import com.fluffy.spring.domain.Category;
import com.fluffy.spring.services.ArticleService;
import com.fluffy.spring.services.CategoryService;
import org.springframework.stereotype.Component;

@Component
public class ArticleForm {
    private String name;
    private String category;
    private String content;

    public ArticleForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article buildArticle(CategoryService categoryService) {
        Article article = new Article();
        article.setName(name);
        article.setCategory(categoryService.findByName(category));
        article.setContent(content);
        return article;
    }
}

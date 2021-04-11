package com.fluffy.spring.controllers;

import com.fluffy.spring.domain.Article;
import com.fluffy.spring.domain.Category;
import com.fluffy.spring.services.ArticleService;
import com.fluffy.spring.services.CategoryService;
import com.fluffy.spring.services.UserService;
import com.fluffy.spring.validation.forms.articles.ArticleForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminUIController {
    private final Validator aritcleFormValidator;
    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final UserService userService;

    public AdminUIController(@Qualifier("articleFormValidator") Validator aritcleFormValidator,
                             ArticleService articleService,
                             CategoryService categoryService,
                             UserService userService) {
        this.aritcleFormValidator = aritcleFormValidator;
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @InitBinder("articleForm")
    protected void signUpFormInitBinder(WebDataBinder binder) {
        binder.setValidator(aritcleFormValidator);
    }

    @GetMapping("/articles/create")
    public String createArticleView(ModelMap modelMap) {
        modelMap.addAttribute("articleForm", new ArticleForm());
        return "articles/create";
    }

    @PostMapping("/articles/create")
    public String createArticle(
            @Valid @ModelAttribute(name = "articleForm") ArticleForm articleForm,
            BindingResult result) {
        if (result.hasErrors()) {
            return "/articles/create";
        }

        String categoryName = articleForm.getCategory();
        if (categoryService.findByName(categoryName) == null) {
            // такої категорії ще не існує - треба її створити
            Category category = new Category();
            category.setName(categoryName);
            categoryService.create(category);
        }

        Article article = articleForm.buildArticle(categoryService);
        article.setUser(userService.findByEmail(AuthController.getCurrentUsername()));
        article.setModificationDate(new java.sql.Date(new Date().getTime()));
        articleService.create(article);
        return "redirect:/";
    }
}

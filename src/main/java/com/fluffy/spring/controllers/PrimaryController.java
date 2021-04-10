package com.fluffy.spring.controllers;

import com.fluffy.spring.services.ArticleService;
import com.fluffy.spring.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrimaryController {
    private final ArticleService articleService;
    private final CategoryService categoryService;

    public PrimaryController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("articles", articleService.findAll());
        modelMap.addAttribute("categories", categoryService.findAll());
        modelMap.addAttribute("mostPopularArticles", articleService.findMostPopular(3));
        return "index";
    }
}

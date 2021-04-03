package com.fluffy.spring.domain;

import com.fluffy.spring.daos.Identified;

public class Comment implements Identified<Integer> {
    private Integer id;
    private Article article;
    private Comment parent;
    private User user;
    private String content;

    @Override
    public Integer getId() {
        return id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

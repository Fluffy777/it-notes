package com.fluffy.spring.daos;

import com.fluffy.spring.domain.Comment;

public interface CommentDAO {
    Comment getById(int commentId);
}

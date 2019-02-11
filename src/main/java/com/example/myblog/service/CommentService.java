package com.example.myblog.service;

import com.example.myblog.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    Comment findById(int id);

    void save(Comment comment);

    void deleteById(int id);
}

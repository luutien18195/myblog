package com.example.myblog.service;

import com.example.myblog.model.Comment;
import com.example.myblog.model.Post;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    List<Comment> findCommentsByPostAndSortById(int id);

    List<Comment> findAllAndSortById();

    Comment findById(int id);

    void save(Comment comment);

    void deleteById(int id);
}

package com.example.myblog.service.impl;

import com.example.myblog.model.Comment;
import com.example.myblog.repository.CommentRepository;
import com.example.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findAll() {
        return this.commentRepository.findAll();
    }

    @Override
    public Comment findById(int id) {
        return this.commentRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Comment comment) {
        this.commentRepository.save(comment);
    }

    @Override
    public void deleteById(int id) {
        this.commentRepository.deleteById(id);
    }
}

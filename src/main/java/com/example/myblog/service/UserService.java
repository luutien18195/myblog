package com.example.myblog.service;

import com.example.myblog.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    User findByUserName(String name);

    void save(User user);

    void deleteById(int id);

}

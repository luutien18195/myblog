package com.example.myblog.service;

import com.example.myblog.model.User;
import org.springframework.validation.Validator;

import java.util.List;

public interface UserService extends Validator {
    List<User> findAll();

    User findById(int id);

    User findUserByEmail(String email);

    User findUserByUserName(String name);

    User findUserByUsernameAndPassword(String username, String password);

    void save(User user);

    void deleteById(int id);

    boolean confirmUser(User user);

}

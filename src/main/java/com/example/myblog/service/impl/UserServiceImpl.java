package com.example.myblog.service.impl;

import com.example.myblog.model.User;
import com.example.myblog.repository.UserRepository;
import com.example.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByUserName(String name) {
        return this.userRepository.findUserByUsername(name);
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        return this.userRepository.findUserByUsernameAndPassword(username,password);
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public boolean confirmUser(User user) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(userRepository.findUserByUsername(user.getUsername())!=null){
            errors.rejectValue("username","username.alreadyExist");
        }

        if(userRepository.findUserByEmail(user.getEmail())!=null){
            errors.rejectValue("email","email.alreadyExist");
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("password","password.wrongPassword");
        }
    }
}

package com.example.myblog.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Validator {

    @Id
    private int id;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "user_name")
    @Size(min = 5, max = 18)
    private String username;

    @Column(name = "password")
    @Size(min = 5, max = 20)
    private String password;

    @Column(name = "confirm_password")
    @Size(min = 5, max = 20)
    private String confirmPassword;

    @Column(name = "avatar_link")
    private String avatar;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    public User() {
        this.avatar = "https://cdn0.iconfinder.com/data/icons/social-flat-rounded-rects/512/anonymous-512.png";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("password","password.wrongPassword");
        }
    }
}

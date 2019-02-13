package com.example.myblog.model;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "follower")
//    @JoinColumn(name = "follower_id")
    private Set<Relationship> active_relationships;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "followed")
//    @JoinColumn(name = "followed_id")
    private Set<Relationship> passive_relationships;

    public User() {
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Relationship> getActive_relationships() {
        return active_relationships;
    }

    public void setActive_relationships(Set<Relationship> active_relationships) {
        this.active_relationships = active_relationships;
    }

    public Set<Relationship> getPassive_relationships() {
        return passive_relationships;
    }

    public void setPassive_relationships(Set<Relationship> passive_relationships) {
        this.passive_relationships = passive_relationships;
    }
}

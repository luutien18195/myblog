package com.example.myblog.model;

import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @Column(name = "date_cre")
    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) //nullable = false;
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private Set<Comment> comments = new HashSet<>();

    public Post() {
        this.image = null;
    }

    public Post(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date.toString();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getPrettyDate(String str) throws ParseException {
        PrettyTime prettyTime = new PrettyTime();
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date!=null?prettyTime.format(date):"";
    }
}

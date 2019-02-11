package com.example.myblog.model;

import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    @Column(name = "comment_id")
    private int comment_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true) //nullable = false;
    private User user;

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

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

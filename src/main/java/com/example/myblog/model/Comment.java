package com.example.myblog.model;

import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPrettyDate() throws ParseException {
        PrettyTime prettyTime = new PrettyTime();
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(this.createdDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date!=null?prettyTime.format(date):"";
    }
}


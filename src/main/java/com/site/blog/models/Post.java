package com.site.blog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title, anons, full_text;
    private int views;
    private Date date;

    public long getId() {
        return id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getAnons() {

        return anons;
    }

    public void setAnons(String anons) {

        this.anons = anons;
    }

    public String getFullText() {

        return full_text;
    }

    public void setFullText(String full_text) {

        this.full_text = full_text;
    }

    public int getViews() {

        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Post() {
    }

    public Post(String title, String anons, String full_text, Date date) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.date = date;
    }
}

package com.site.blog.models;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Setter
@ToString
@Table(name = "articles")
public class Articles {

    private long id;
    private String title, anons, full_text;
    private int views;
    private Date date_publication;
    private Users users_id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Basic
    @Column(name = "anons")
    public String getAnons() {
        return anons;
    }

    @Basic
    @Column(name = "full_text")
    public String getFull_text() {
        return full_text;
    }

    @Basic
    @Column(name = "views")
    public int getViews() {
        return views;
    }

    @Basic
    @Column(name = "date_publication")
    public Date getDate_publication() {
        return date_publication;
    }

    @ManyToOne(cascade= {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "users_id", referencedColumnName = "username")
    public Users getUsers_id() {
        return users_id;
    }

    public Articles(String title, String anons, String full_text, Users users_id) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.users_id = users_id;
    }
}

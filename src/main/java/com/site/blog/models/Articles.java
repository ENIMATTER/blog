package com.site.blog.models;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@Table(name = "articles")
public class Articles {

    private long id;
    private String title, anons, full_text;
    private int views;
    private Date date_publication;
    private People people_id;

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

    @ManyToOne
    @JoinColumn(name = "people_id", referencedColumnName = "id")
    public People getPeople_id() {
        return people_id;
    }

    public Articles(String title, String anons, String full_text, Date date_publication, People people_id) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.date_publication = date_publication;
        this.people_id = people_id;
    }
}

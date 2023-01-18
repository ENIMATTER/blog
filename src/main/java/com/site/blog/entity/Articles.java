package com.site.blog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "articles")
public class Articles {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Size(max = 100, message = "Title size must be less than 100")
    @NotBlank(message = "Title is mandatory")
    @Basic
    @Column(name = "title")
    private String title;

    @Size(max = 250, message = "Anons size must be less than 250")
    @Basic
    @Column(name = "anons")
    private String anons;

    @Size(max = 2000, message = "Full text size must be less than 2000")
    @Basic
    @Column(name = "full_text")
    private String full_text;

    @Basic
    @Column(name = "views")
    private long views;

    @Basic
    @Column(name = "date_publication")
    private Date date_publication;

    @ManyToOne(cascade= {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "users_id", referencedColumnName = "username")
    private Users users_id;

    public Articles(String title, String anons, String full_text, Users users_id) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.users_id = users_id;
    }
}

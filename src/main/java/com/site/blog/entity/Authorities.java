package com.site.blog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "authorities")
public class Authorities {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade= {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Users username;

    @Basic
    @Column(name = "authority")
    private String authority;

    public Authorities(Users username, String authority) {
        this.username = username;
        this.authority = authority;
    }
}

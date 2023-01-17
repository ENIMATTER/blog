package com.site.blog.models;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@ToString
@Table(name = "authorities")
public class Authorities {
    private long id;
    private Users username;
    private String authority;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    @ManyToOne(cascade= {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "username", referencedColumnName = "username")
    public Users getUsername() {
        return username;
    }

    @Basic
    @Column(name = "authority")
    public String getAuthority() {
        return authority;
    }

    public Authorities(Users username, String authority) {
        this.username = username;
        this.authority = authority;
    }
}

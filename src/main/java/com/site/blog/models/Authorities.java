package com.site.blog.models;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
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

    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.DETACH})
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

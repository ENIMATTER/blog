package com.site.blog.models;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class People {

    private long id;
    private String username, password, name_people, surname_people;
    private Roles role_id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Basic
    @Column(name = "name_people")
    public String getName_people() {
        return name_people;
    }

    @Basic
    @Column(name = "surname_people")
    public String getSurname_people() {
        return surname_people;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    public Roles getRole_id() {
        return role_id;
    }

    public People(String username, String password, String name_people, String surname_people, Roles role_id) {
        this.username = username;
        this.password = password;
        this.name_people = name_people;
        this.surname_people = surname_people;
        this.role_id = role_id;
    }

    public People(Roles role_id) {
        this.role_id = role_id;
    }
}

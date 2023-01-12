package com.site.blog.models;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@Table(name = "people")
public class People {

    private long id;
    private String name_people, surname_people;
    private Users users_id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long getId() {
        return id;
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

    @OneToOne
    @JoinColumn(name = "users_id", referencedColumnName = "username")
    public Users getUsers_id() {
        return users_id;
    }

    public People(String name_people, String surname_people, Users users_id) {
        this.name_people = name_people;
        this.surname_people = surname_people;
        this.users_id = users_id;
    }
}

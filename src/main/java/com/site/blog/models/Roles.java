package com.site.blog.models;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class Roles {

    private long id;
    private String name_role;

    public Roles(String name_role) {
        this.name_role = name_role;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    @Basic
    @Column(name = "name_role")
    public String getName_role() {
        return name_role;
    }

}

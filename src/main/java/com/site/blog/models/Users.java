package com.site.blog.models;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "users")
public class Users {
    private String username, password, name, surname, email;
    private int enabled;

    @Id
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
    @Column(name = "enabled")
    public int getEnabled() {
        return enabled;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }
}

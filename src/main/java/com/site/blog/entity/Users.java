package com.site.blog.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "username")
    @Size(max = 50, message = "Username size must be less than 50")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Basic
    @Column(name = "password")
    @Size(max = 100, message = "Password size must be less than 50")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Basic
    @Column(name = "enabled")
    @Max(value = 1, message = "Enabled size must be less than 1")
    @Min(value = 0, message = "Enabled size must be greater than 0")
    @NotNull(message = "Enabled is mandatory")
    private int enabled;

    @Basic
    @Column(name = "name")
    @Size(max = 50, message = "Name size must be less than 50")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Basic
    @Column(name = "surname")
    @Size(max = 50, message = "Surname size must be less than 50")
    @NotBlank(message = "Surname is mandatory")
    private String surname;

    //TODO: do custom Email validation
    @Basic
    @Column(name = "email")
    @Email(message = "Email incorrect")
    @Size(max = 50, message = "Email size must be less than 50")
    @NotBlank(message = "Email is mandatory")
    private String email;
}

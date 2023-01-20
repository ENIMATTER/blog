package com.site.blog.entity;

import com.site.blog.validation.CheckUsername;
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

    @CheckUsername
    @Size(max = 50, message = "Username size must be less than 50")
    @NotBlank(message = "Username is mandatory")
    @Id
    @Column(name = "username")
    private String username;

    @Size(max = 100, message = "Password size must be less than 50")
    @NotBlank(message = "Password is mandatory")
    @Basic
    @Column(name = "password")
    private String password;

    @Max(value = 1, message = "Enabled size must be less than 1")
    @Min(value = 0, message = "Enabled size must be greater than 0")
    @NotNull(message = "Enabled is mandatory")
    @Basic
    @Column(name = "enabled")
    private int enabled;

    @Size(max = 50, message = "Name size must be less than 50")
    @NotBlank(message = "Name is mandatory")
    @Basic
    @Column(name = "name")
    private String name;

    @Size(max = 50, message = "Surname size must be less than 50")
    @NotBlank(message = "Surname is mandatory")
    @Basic
    @Column(name = "surname")
    private String surname;

    @Pattern(regexp = "\\w+\\.?\\w*@[a-z]+\\.[a-z]+", message = "Email must be in this pattern: example@abc.xyz")
    @Size(max = 50, message = "Email size must be less than 50")
    @Basic
    @Column(name = "email")
    private String email;
}

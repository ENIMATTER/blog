package com.site.blog.validation;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UsernameClass {
    @CheckUsername
    @Size(max = 50, message = "Username size must be less than 50")
    @NotBlank(message = "Username is mandatory")
    private String username;
}

package com.site.blog.validation;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserEditValidation {
    @Max(value = 1, message = "Enabled size must be less than 1")
    @Min(value = 0, message = "Enabled size must be greater than 0")
    @NotNull(message = "Enabled is mandatory")
    private int enabled;

    @Size(max = 50, message = "Name size must be less than 50")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(max = 50, message = "Surname size must be less than 50")
    @NotBlank(message = "Surname is mandatory")
    private String surname;

    @Pattern(regexp = "\\w+\\.?\\w*@[a-z]+\\.[a-z]+", message = "Email must be in this pattern: example@abc.xyz")
    @Size(max = 50, message = "Email size must be less than 50")
    private String email;
}

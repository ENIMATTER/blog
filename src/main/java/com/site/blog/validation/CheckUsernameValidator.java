package com.site.blog.validation;

import com.site.blog.entity.Users;
import com.site.blog.service.UsersService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class CheckUsernameValidator implements ConstraintValidator<CheckUsername, String> {
    private final UsersService usersService;
    public CheckUsernameValidator(UsersService usersService){
        this.usersService = usersService;
    }

    @Override
    public boolean isValid(String enteredValue, ConstraintValidatorContext constraintValidatorContext) {
        List<Users> users = usersService.findAllByOrderByUsernameAsc();
        for (Users user : users) {
            if (enteredValue.trim().equals(user.getUsername())) {
                return false;
            }
        }
        return true;
    }
}
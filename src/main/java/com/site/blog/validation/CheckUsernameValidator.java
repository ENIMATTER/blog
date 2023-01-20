package com.site.blog.validation;

import com.site.blog.entity.Users;
import com.site.blog.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CheckUsernameValidator implements ConstraintValidator<CheckUsername, String> {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public boolean isValid(String enteredValue, ConstraintValidatorContext constraintValidatorContext) {
        Iterable<Users> users = usersRepository.findAll();
        for (Users user : users) {
            if (enteredValue.trim().equals(user.getUsername())) {
                return false;
            }
        }
        return true;
    }
}
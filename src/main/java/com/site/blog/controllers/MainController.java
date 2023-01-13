package com.site.blog.controllers;

import com.site.blog.models.Authorities;
import com.site.blog.models.People;
import com.site.blog.models.Users;
import com.site.blog.repo.AuthoritiesRepository;
import com.site.blog.repo.PeopleRepository;
import com.site.blog.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "aboutUs";
    }

    @GetMapping("/logon")
    public String logon() {
        return "logon";
    }

    @PostMapping("/logon")
    public String logonPost(@RequestParam String name_people, @RequestParam String surname_people,
                            @RequestParam String username, @RequestParam String password,
                            @RequestParam String[] authority) {
        Iterable<Users> usersIterable = usersRepository.findAll();
        for (Users user : usersIterable) {
            if(user.getUsername().equals(username)){
                return "redirect:/logon";
            }
        }

        String codedPassword = "{noop}" + password;
        Users users = new Users(username, codedPassword, 1);
        People people = new People(name_people, surname_people, users);

        List<Authorities> authoritiesList = new ArrayList<>();
        for(String role : authority) {
            authoritiesList.add(new Authorities(users, role));
        }

        usersRepository.save(users);
        peopleRepository.save(people);
        authoritiesRepository.saveAll(authoritiesList);

        return "redirect:/";
    }
}

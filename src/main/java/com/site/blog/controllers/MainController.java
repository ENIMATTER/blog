package com.site.blog.controllers;

import com.site.blog.models.People;
import com.site.blog.models.Roles;
import com.site.blog.repo.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private PeopleRepository peopleRepository;

    public static People initialHuman = new People(new Roles());

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("initialRole", initialHuman.getRole_id());
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("initialRole", initialHuman.getRole_id());
        return "aboutUs";
    }

    @GetMapping("/log-in")
    public String logInGet() {
        return "log-in";
    }

    @PostMapping("/log-in")
    public String logInPost(@RequestParam String username, @RequestParam String password) {
        Iterable<People> peopleRepo = peopleRepository.findAll();
        for(People people : peopleRepo){
            if(username.equals(people.getUsername()) && password.equals(people.getPassword())){
                initialHuman = people;
            }
        }
        return "redirect:/";
    }

    @PostMapping("/log-out")
    public String postLogOut() {
        initialHuman = new People(new Roles());
        return "redirect:/";
    }

}

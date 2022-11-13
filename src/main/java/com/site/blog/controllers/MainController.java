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

    public static Roles initialRole = new Roles();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("initialRole", initialRole);
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("initialRole", initialRole);
        return "aboutUs";
    }

    @GetMapping("/log-in")
    public String logInGet() {
        return "log-in";
    }

    @PostMapping("/log-in")
    public String logInPost(@RequestParam String name_people, @RequestParam String surname_people,
                            @RequestParam String role_id) {
        Iterable<People> peopleRepo = peopleRepository.findAll();
        for(People people : peopleRepo){
            if(name_people.equals(people.getName_people()) && surname_people.equals(people.getSurname_people())
                    && role_id.equals(people.getRole_id().getName_role())){
                initialRole = people.getRole_id();
            }
        }
        return "redirect:/";
    }

    @PostMapping("/log-out")
    public String postLogOut() {
        initialRole = new Roles();
        return "redirect:/";
    }

}

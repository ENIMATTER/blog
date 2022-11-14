package com.site.blog.controllers;

import com.site.blog.models.People;
import com.site.blog.models.Roles;
import com.site.blog.repo.PeopleRepository;
import com.site.blog.repo.RolesRepository;
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

    @Autowired
    private RolesRepository rolesRepository;

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

    @GetMapping("/log-on")
    public String logOnGet() {
        return "log-on";
    }

    @PostMapping("/log-on")
    public String logOnPost(@RequestParam String username, @RequestParam String password,
                            @RequestParam String name_people, @RequestParam String surname_people,
                            @RequestParam String role_id) {
        Iterable<Roles> roles = rolesRepository.findAll();

        for(Roles role : roles){
            if(role.getName_role().equals(role_id) && !role_id.equals("Admin")){
                People people = new People(username, password, name_people, surname_people, role);
                peopleRepository.save(people);
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

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
public class PeopleController {
    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping("/people")
    public String peopleMain(Model model) {
        Iterable<People> people = peopleRepository.findAll();
        model.addAttribute("people", people);
        return "people-main";
    }

    @GetMapping("/people/add")
    public String peopleAdd() {
        return "people-add";
    }

    @PostMapping("/people/add")
    public String peoplePostAdd(@RequestParam String name_people, @RequestParam String surname_people, @RequestParam String role_id) {
        Iterable<Roles> roles = rolesRepository.findAll();

        for(Roles role : roles){
            if(role.getName_role().equals(role_id)){
                People people = new People(name_people, surname_people, role);
                peopleRepository.save(people);
            }
        }
        return "redirect:/people";
    }
}

package com.site.blog.controllers;

import com.site.blog.models.Articles;
import com.site.blog.models.People;
import com.site.blog.models.Roles;
import com.site.blog.repo.ArticlesRepository;
import com.site.blog.repo.PeopleRepository;
import com.site.blog.repo.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.site.blog.controllers.MainController.initialHuman;

@Controller
public class PeopleController {
    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ArticlesRepository articlesRepository;

    @GetMapping("/people")
    public String peopleMain(Model model) {
        Iterable<People> people = peopleRepository.findAll();
        model.addAttribute("people", people);
        model.addAttribute("initialRole", initialHuman.getRole_id());
        return "people-templates/people-main";
    }

    @GetMapping("/people/add")
    public String peopleAdd(Model model) {
        model.addAttribute("initialRole", initialHuman.getRole_id());
        return "people-templates/people-add";
    }

    @PostMapping("/people/add")
    public String peoplePostAdd(@RequestParam String username, @RequestParam String password,
                                @RequestParam String name_people, @RequestParam String surname_people,
                                @RequestParam String role_id) {
        Iterable<Roles> roles = rolesRepository.findAll();

        for(Roles role : roles){
            if(role.getName_role().equals(role_id)){
                People people = new People(username, password, name_people, surname_people, role);
                peopleRepository.save(people);
            }
        }
        return "redirect:/people";
    }

    @GetMapping("/people/{id}/edit")
    public String peopleEdit(@PathVariable(value = "id") long id, Model model) {
        if (!peopleRepository.existsById(id)) {
            return "redirect:/people";
        }
        People people = peopleRepository.findById(id).orElseThrow();
        model.addAttribute("people", people);
        model.addAttribute("initialRole", initialHuman.getRole_id());
        return "people-templates/people-edit";
    }

    @PostMapping("/people/{id}/edit")
    public String peoplePostEdit(@PathVariable(value = "id") long id, @RequestParam String username,
                                 @RequestParam String password, @RequestParam String name_people,
                                 @RequestParam String surname_people, @RequestParam String role_id) {
        People people = peopleRepository.findById(id).orElseThrow();
        Iterable<Roles> roles = rolesRepository.findAll();
        for(Roles role : roles){
            if(role.getName_role().equals(role_id)){
                people.setUsername(username);
                people.setPassword(password);
                people.setName_people(name_people);
                people.setSurname_people(surname_people);
                people.setRole_id(role);
                peopleRepository.save(people);
            }
        }
        return "redirect:/people";
    }

    @PostMapping("/people/{id}/remove")
    public String peoplePostDelete(@PathVariable(value = "id") long id) {
        People people = peopleRepository.findById(id).orElseThrow();
        Iterable<Articles> articlesRepo = articlesRepository.findAll();
        for(Articles articles : articlesRepo){
            if(articles.getPeople_id().getId() == people.getId()){
                articlesRepository.delete(articles);
            }
        }
        peopleRepository.delete(people);
        return "redirect:/people";
    }
}

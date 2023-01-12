package com.site.blog.controllers;

import com.site.blog.models.Articles;
import com.site.blog.models.People;
import com.site.blog.models.Users;
import com.site.blog.repo.ArticlesRepository;
import com.site.blog.repo.PeopleRepository;
import com.site.blog.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PeopleController {
    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/people")
    public String peopleMain(Model model) {
        Iterable<People> people = peopleRepository.findAll();
        model.addAttribute("people", people);
        return "people-templates/people-main";
    }

    @GetMapping("/people/add")
    public String peopleAdd() {
        return "people-templates/people-add";
    }

    @PostMapping("/people/add")
    public String peoplePostAdd(@RequestParam String name_people, @RequestParam String surname_people,
                                @RequestParam String username, @RequestParam String password) {

        Users users = new Users(username, password, 1);
        usersRepository.save(users);

        People people = new People(name_people, surname_people, users);
        peopleRepository.save(people);

        return "redirect:/people";
    }

    @GetMapping("/people/{id}/edit")
    public String peopleEdit(@PathVariable(value = "id") long id, Model model) {
        if (!peopleRepository.existsById(id)) {
            return "redirect:/people";
        }
        People people = peopleRepository.findById(id).orElseThrow();
        model.addAttribute("people", people);
        return "people-templates/people-edit";
    }

    @PostMapping("/people/{id}/edit")
    public String peoplePostEdit(@PathVariable(value = "id") long id, @RequestParam String name_people,
                                 @RequestParam String surname_people, @RequestParam String username,
                                 @RequestParam String password, @RequestParam int enabled) {
        People people = peopleRepository.findById(id).orElseThrow();
        Users newUser = new Users(username, password, enabled);
        usersRepository.save(newUser);
        Users oldUsers = usersRepository.findById(people.getUsers_id().getUsername()).orElseThrow();

        people.setName_people(name_people);
        people.setSurname_people(surname_people);
        people.setUsers_id(newUser);
        peopleRepository.save(people);

        usersRepository.delete(oldUsers);
        return "redirect:/people";
    }

    @PostMapping("/people/{id}/remove")
    public String peoplePostDelete(@PathVariable(value = "id") long id) {
        People people = peopleRepository.findById(id).orElseThrow();
        Iterable<Articles> articlesRepo = articlesRepository.findAll();
        for (Articles articles : articlesRepo) {
            if (articles.getPeople_id().getId() == people.getId()) {
                articlesRepository.delete(articles);
            }
        }
        peopleRepository.delete(people);
        return "redirect:/people";
    }
}

package com.site.blog.controllers;

import com.site.blog.models.Authorities;
import com.site.blog.models.People;
import com.site.blog.models.Users;
import com.site.blog.repo.ArticlesRepository;
import com.site.blog.repo.AuthoritiesRepository;
import com.site.blog.repo.PeopleRepository;
import com.site.blog.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PeopleController {
    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

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
                                @RequestParam String username, @RequestParam String password,
                                @RequestParam String[] authority) {

        Iterable<Users> usersIterable = usersRepository.findAll();
        for (Users user : usersIterable) {
            if(user.getUsername().equals(username)){
                return "redirect:/people/add";
            }
        }

        String codedPassword = "{noop}" + password;
        Users users = new Users(username, codedPassword, 1);
        People people = new People(name_people, surname_people, users);

        List<Authorities> authoritiesList = new ArrayList<>();
        for(String role : authority) {
            authoritiesList.add(new Authorities(users, role));
        }

        peopleRepository.save(people);
        authoritiesRepository.saveAll(authoritiesList);

        return "redirect:/people";
    }

    @GetMapping("/people/{id}/edit")
    public String peopleEdit(@PathVariable(value = "id") long id, Model model) {
        if (!peopleRepository.existsById(id)) {
            return "redirect:/people";
        }
        People people = peopleRepository.findById(id).orElseThrow();
        model.addAttribute("people", people);

        Iterable<Authorities> authorities = authoritiesRepository.findAll();
        List<String> authoritiesStringList = new ArrayList<>();
        for(Authorities authority : authorities){
            if(people.getUsers_id().getUsername().equals(authority.getUsername().getUsername())){
                authoritiesStringList.add(authority.getAuthority());
            }
        }

        model.addAttribute("authoritiesStringList", authoritiesStringList);

        return "people-templates/people-edit";
    }

    @PostMapping("/people/{id}/edit")
    public String peoplePostEdit(@PathVariable(value = "id") long id, @RequestParam String name_people,
                                 @RequestParam String surname_people, @RequestParam String username,
                                 @RequestParam String password, @RequestParam int enabled,
                                 @RequestParam String[] authority) {

        People people = peopleRepository.findById(id).orElseThrow();

        Iterable<Authorities> authoritiesIterable = authoritiesRepository.findAll();
        Users oldUser = people.getUsers_id();
        Users newUser = new Users(username, password, enabled);

        people.setName_people(name_people);
        people.setSurname_people(surname_people);
        people.setUsers_id(newUser);
        peopleRepository.save(people);

        List<Authorities> newAuthoritiesList = new ArrayList<>();
        List<Authorities> oldAuthoritiesList = new ArrayList<>();
        for(Authorities authorities : authoritiesIterable){
            if(oldUser.getUsername().equals(authorities.getUsername().getUsername())){
                newAuthoritiesList.add(new Authorities(newUser, authorities.getAuthority()));
                oldAuthoritiesList.add(authorities);
            }
        }

        authoritiesRepository.deleteAll(oldAuthoritiesList);
        usersRepository.delete(oldUser);
        authoritiesRepository.saveAll(newAuthoritiesList);

        return "redirect:/people";
    }

    @PostMapping("/people/{id}/remove")
    public String peoplePostDelete(@PathVariable(value = "id") long id) {
        peopleRepository.deleteById(id);
        return "redirect:/people";
    }
}

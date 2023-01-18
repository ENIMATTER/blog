package com.site.blog.controllers;

import com.site.blog.models.Authorities;
import com.site.blog.models.Users;
import com.site.blog.repo.AuthoritiesRepository;
import com.site.blog.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @GetMapping("/users")
    public String usersMain(Model model) {
        Iterable<Users> users = usersRepository.findAll();
        model.addAttribute("users", users);
        return "users-templates/users-main";
    }

    @GetMapping("/users/add")
    public String usersAdd(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "users-templates/users-add";
    }

    @PostMapping("/users/add")
    public String usersPostAdd(@ModelAttribute("user") Users user, @RequestParam String[] authority) {
        Iterable<Users> usersIterable = usersRepository.findAll();
        for (Users users : usersIterable) {
            if(users.getUsername().equals(user.getUsername())){
                return "redirect:/";
            }
        }
        String codedPassword = "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(codedPassword);
        user.setEnabled(1);
        List<Authorities> authoritiesList = new ArrayList<>();
        for(String role : authority) {
            authoritiesList.add(new Authorities(user, role));
        }
        authoritiesRepository.saveAll(authoritiesList);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String usersEdit(@PathVariable(value = "id") String id, Model model) {
        if (!usersRepository.existsById(id)) {
            return "redirect:/users";
        }
        Users user = usersRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "users-templates/users-edit";
    }

    @PostMapping("/users/{id}/edit")
    public String usersPostEdit(@PathVariable(value = "id") String id, @ModelAttribute("user") Users user) {
        Users editedUser = usersRepository.findById(id).orElseThrow();
        editedUser.setEnabled(user.getEnabled());
        editedUser.setName(user.getName());
        editedUser.setSurname(user.getSurname());
        editedUser.setEmail(user.getEmail());
        usersRepository.save(editedUser);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit/authority")
    public String usersEditAuthority(@PathVariable(value = "id") String id, Model model) {
        if (!usersRepository.existsById(id)) {
            return "redirect:/users";
        }
        Users user = usersRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        List<Authorities> authorities = authoritiesRepository.findAllByUsername(user);
        model.addAttribute("authorities", authorities);
        return "users-templates/users-edit-authority";
    }

    @PostMapping("/users/{id}/edit/authority")
    public String usersPostEditAuthority(@PathVariable(value = "id") String id, @RequestParam String[] authorities) {
        Users user = usersRepository.findById(id).orElseThrow();
        List<Authorities> oldAuthoritiesList = authoritiesRepository.findAllByUsername(user);
        authoritiesRepository.deleteAll(oldAuthoritiesList);
        List<Authorities> newAuthoritiesList = new ArrayList<>();
        for(String role : authorities) {
            newAuthoritiesList.add(new Authorities(user, role));
        }
        authoritiesRepository.saveAll(newAuthoritiesList);
        return "redirect:/users/{id}/edit";
    }

    @GetMapping("/users/{id}/edit/password")
    public String usersEditPassword(@PathVariable(value = "id") String id, Model model) {
        if (!usersRepository.existsById(id)) {
            return "redirect:/users";
        }
        Users user = usersRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "users-templates/users-edit-password";
    }

    @PostMapping("/users/{id}/edit/password")
    public String usersPostEditPassword(@PathVariable(value = "id") String id, @RequestParam String password) {
        Users user = usersRepository.findById(id).orElseThrow();
        String codedPassword = "{bcrypt}" + new BCryptPasswordEncoder().encode(password);
        user.setPassword(codedPassword);
        usersRepository.save(user);
        return "redirect:/users/{id}/edit";
    }

    @GetMapping("/users/{id}/edit/username")
    public String usersEditUsername(@PathVariable(value = "id") String id, Model model) {
        if (!usersRepository.existsById(id)) {
            return "redirect:/users";
        }
        Users user = usersRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "users-templates/users-edit-username";
    }

    @PostMapping("/users/{id}/edit/username")
    public String usersPostEditUsername(@PathVariable(value = "id") String id, @RequestParam String username) {
        Users user = usersRepository.findById(id).orElseThrow();
        usersRepository.changeUsername(user.getUsername(), username);
        return "redirect:/login?logout";
    }

    @PostMapping("/users/{id}/remove")
    public String usersPostDelete(@PathVariable(value = "id") String id) {
        usersRepository.deleteById(id);
        return "redirect:/users";
    }
}

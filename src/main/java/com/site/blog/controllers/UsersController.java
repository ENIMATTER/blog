package com.site.blog.controllers;

import com.site.blog.entity.Authorities;
import com.site.blog.entity.Users;
import com.site.blog.repo.AuthoritiesRepository;
import com.site.blog.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String usersPostAdd(@Valid @ModelAttribute("user") Users user, BindingResult bindingResult,
                               @RequestParam String[] authority) {
        if (bindingResult.hasErrors()) {
            return "users-templates/users-add";
        }
        Iterable<Users> usersIterable = usersRepository.findAll();
        for (Users users : usersIterable) {
            if(users.getUsername().equals(user.getUsername())){
                return "redirect:/users";
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

    @GetMapping("/users/{username}/edit")
    public String usersEdit(@PathVariable(value = "username") String username, Model model) {
        if (!usersRepository.existsById(username)) {
            return "redirect:/users";
        }
        Users user = usersRepository.findById(username).orElseThrow();
        model.addAttribute("user", user);
        return "users-templates/users-edit";
    }

    @PostMapping("/users/{username}/edit")
    public String usersPostEdit(@PathVariable(value = "username") String username,
                                @Valid @ModelAttribute("user") Users user,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users-templates/users-edit";
        }
        Users editedUser = usersRepository.findById(username).orElseThrow();
        editedUser.setEnabled(user.getEnabled());
        editedUser.setName(user.getName());
        editedUser.setSurname(user.getSurname());
        editedUser.setEmail(user.getEmail());
        usersRepository.save(editedUser);
        return "redirect:/users";
    }

    @GetMapping("/users/{username}/edit/authority")
    public String usersEditAuthority(@PathVariable(value = "username") String username, Model model) {
        if (!usersRepository.existsById(username)) {
            return "redirect:/users";
        }
        Users user = usersRepository.findById(username).orElseThrow();
        model.addAttribute("user", user);
        List<Authorities> authorities = authoritiesRepository.findAllByUsername(user);
        model.addAttribute("authorities", authorities);
        return "users-templates/users-edit-authority";
    }

    @PostMapping("/users/{username}/edit/authority")
    public String usersPostEditAuthority(@PathVariable(value = "username") String username,
                                         @RequestParam String[] authorities) {
        Users user = usersRepository.findById(username).orElseThrow();
        List<Authorities> oldAuthoritiesList = authoritiesRepository.findAllByUsername(user);
        authoritiesRepository.deleteAll(oldAuthoritiesList);
        List<Authorities> newAuthoritiesList = new ArrayList<>();
        for(String role : authorities) {
            newAuthoritiesList.add(new Authorities(user, role));
        }
        authoritiesRepository.saveAll(newAuthoritiesList);
        return "redirect:/users/{username}/edit";
    }

    @GetMapping("/users/{username}/edit/password")
    public String usersEditPassword(@PathVariable(value = "username") String username, Model model) {
        if (!usersRepository.existsById(username)) {
            return "redirect:/users";
        }
        model.addAttribute("username", username);
        return "users-templates/users-edit-password";
    }

    @PostMapping("/users/{username}/edit/password")
    public String usersPostEditPassword(@PathVariable(value = "username") String username,
                                        @RequestParam String password) {
        if (password.isBlank() || password.length() > 50) {
            return "users-templates/users-edit-password";
        }
        Users user = usersRepository.findById(username).orElseThrow();
        String codedPassword = "{bcrypt}" + new BCryptPasswordEncoder().encode(password);
        user.setPassword(codedPassword);
        usersRepository.save(user);
        return "redirect:/users/{username}/edit";
    }

    @GetMapping("/users/{username}/edit/username")
    public String usersEditUsername(@PathVariable(value = "username") String username, Model model) {
        if (!usersRepository.existsById(username)) {
            return "redirect:/users";
        }
        model.addAttribute("username", username);
        return "users-templates/users-edit-username";
    }

    @PostMapping("/users/{username}/edit/username")
    public String usersPostEditUsername(@PathVariable(value = "username") String username,
                                        @RequestParam String username1) {
        if (username1.isBlank() || username1.length() > 50) {
            return "users-templates/users-edit-username";
        }
        Users user = usersRepository.findById(username).orElseThrow();
        usersRepository.changeUsername(user.getUsername(), username1);
        return "redirect:/users";
    }

    @PostMapping("/users/{username}/remove")
    public String usersPostDelete(@PathVariable(value = "username") String username) {
        usersRepository.deleteById(username);
        return "redirect:/users";
    }
}

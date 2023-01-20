package com.site.blog.controller;

import com.site.blog.service.AuthoritiesService;
import com.site.blog.service.UsersService;
import com.site.blog.validation.UserEditValidation;
import com.site.blog.validation.UsernameClass;
import com.site.blog.entity.Authorities;
import com.site.blog.entity.Users;
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
    private final UsersService usersService;

    private final AuthoritiesService authoritiesService;

    public UsersController(UsersService usersService, AuthoritiesService authoritiesService) {
        this.usersService = usersService;
        this.authoritiesService = authoritiesService;
    }

    @GetMapping("/users")
    public String usersMain(Model model) {
        Iterable<Users> users = usersService.findAllByOrderByUsernameAsc();
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
        String codedPassword = "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(codedPassword);
        user.setEnabled(1);
        List<Authorities> authoritiesList = new ArrayList<>();
        for (String role : authority) {
            authoritiesList.add(new Authorities(user, role));
        }
        authoritiesService.saveAll(authoritiesList);
        return "redirect:/users";
    }

    @GetMapping("/users/{username}/edit")
    public String usersEdit(@PathVariable(value = "username") String username, Model model) {
        if (!usersService.existsById(username)) {
            return "redirect:/users";
        }
        Users currentUser = usersService.findById(username);
        UserEditValidation user = new UserEditValidation();
        user.setEnabled(currentUser.getEnabled());
        user.setName(currentUser.getName());
        user.setSurname(currentUser.getSurname());
        user.setEmail(currentUser.getEmail());
        model.addAttribute("user", user);
        return "users-templates/users-edit";
    }

    @PostMapping("/users/{username}/edit")
    public String usersPostEdit(@PathVariable(value = "username") String username,
                                @Valid @ModelAttribute("user") UserEditValidation user,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users-templates/users-edit";
        }
        Users editedUser = usersService.findById(username);
        editedUser.setEnabled(user.getEnabled());
        editedUser.setName(user.getName());
        editedUser.setSurname(user.getSurname());
        editedUser.setEmail(user.getEmail());
        usersService.update(editedUser);
        return "redirect:/users";
    }

    @GetMapping("/users/{username}/edit/authority")
    public String usersEditAuthority(@PathVariable(value = "username") String username, Model model) {
        if (!usersService.existsById(username)) {
            return "redirect:/users";
        }
        Users user = usersService.findById(username);
        model.addAttribute("user", user);
        List<Authorities> authorities = authoritiesService.findAllByUsername(user);
        model.addAttribute("authorities", authorities);
        return "users-templates/users-edit-authority";
    }

    @PostMapping("/users/{username}/edit/authority")
    public String usersPostEditAuthority(@PathVariable(value = "username") String username,
                                         @RequestParam String[] authorities) {
        Users user = usersService.findById(username);
        List<Authorities> oldAuthoritiesList = authoritiesService.findAllByUsername(user);
        authoritiesService.deleteAll(oldAuthoritiesList);
        List<Authorities> newAuthoritiesList = new ArrayList<>();
        for (String role : authorities) {
            newAuthoritiesList.add(new Authorities(user, role));
        }
        authoritiesService.saveAll(newAuthoritiesList);
        return "redirect:/users/{username}/edit";
    }

    @GetMapping("/users/{username}/edit/password")
    public String usersEditPassword(@PathVariable(value = "username") String username, Model model) {
        if (!usersService.existsById(username)) {
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
        Users user = usersService.findById(username);
        String codedPassword = "{bcrypt}" + new BCryptPasswordEncoder().encode(password);
        user.setPassword(codedPassword);
        usersService.update(user);
        return "redirect:/users/{username}/edit";
    }

    @GetMapping("/users/{username}/edit/username")
    public String usersEditUsername(@PathVariable(value = "username") String username, Model model) {
        if (!usersService.existsById(username)) {
            return "redirect:/users";
        }
        model.addAttribute("username", username);
        UsernameClass username1 = new UsernameClass();
        model.addAttribute("username1", username1);
        return "users-templates/users-edit-username";
    }

    @PostMapping("/users/{username}/edit/username")
    public String usersPostEditUsername(@PathVariable(value = "username") String username,
                                        @Valid @ModelAttribute("username1") UsernameClass username1,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users-templates/users-edit-username";
        }
        Users user = usersService.findById(username);
        usersService.changeUsername(user.getUsername(), username1.getUsername());
        return "redirect:/users";
    }

    @PostMapping("/users/{username}/remove")
    public String usersPostDelete(@PathVariable(value = "username") String username) {
        usersService.deleteById(username);
        return "redirect:/users";
    }
}

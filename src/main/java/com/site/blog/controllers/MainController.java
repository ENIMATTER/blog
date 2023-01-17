package com.site.blog.controllers;

import com.site.blog.models.Authorities;
import com.site.blog.models.Users;
import com.site.blog.repo.AuthoritiesRepository;
import com.site.blog.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "aboutUs";
    }

    @GetMapping("/registration")
    public String logon(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String logonPost(@ModelAttribute("user") Users user, @RequestParam String[] authority) {
        Iterable<Users> usersIterable = usersRepository.findAll();
        for (Users users : usersIterable) {
            if(users.getUsername().equals(user.getUsername())){
                return "redirect:/registration";
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
        return "redirect:/";
    }
}

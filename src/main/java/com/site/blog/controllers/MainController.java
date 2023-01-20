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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
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
    public String logonPost(@Valid @ModelAttribute("user") Users user,
                            BindingResult bindingResult, @RequestParam String[] authority) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        String codedPassword = "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(codedPassword);
        user.setEnabled(1);
        List<Authorities> authoritiesList = new ArrayList<>();
        for (String role : authority) {
            authoritiesList.add(new Authorities(user, role));
        }
        authoritiesRepository.saveAll(authoritiesList);
        return "redirect:/login";
    }
}

package com.site.blog.controller;

import com.site.blog.entity.Authorities;
import com.site.blog.entity.Users;
import com.site.blog.service.AuthoritiesService;
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

    private final AuthoritiesService authoritiesService;

    public MainController(AuthoritiesService authoritiesService){
        this.authoritiesService = authoritiesService;
    }

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
        authoritiesService.saveAll(authoritiesList);
        return "redirect:/login";
    }
}

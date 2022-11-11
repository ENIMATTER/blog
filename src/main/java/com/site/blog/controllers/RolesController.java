package com.site.blog.controllers;

import com.site.blog.models.People;
import com.site.blog.models.Roles;
import com.site.blog.repo.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RolesController {

    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping("/roles")
    public String rolesMain(Model model) {
        Iterable<Roles> roles = rolesRepository.findAll();
        model.addAttribute("roles", roles);
        return "roles-main";
    }

    @GetMapping("/roles/add")
    public String rolesAdd() {
        return "roles-add";
    }

    @PostMapping("/roles/add")
    public String rolesPostAdd(@RequestParam String name_role) {
        Roles roles = new Roles(name_role);
        rolesRepository.save(roles);
        return "redirect:/roles";
    }
}

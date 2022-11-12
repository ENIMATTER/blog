package com.site.blog.controllers;

import com.site.blog.models.People;
import com.site.blog.models.Roles;
import com.site.blog.repo.PeopleRepository;
import com.site.blog.repo.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RolesController {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping("/roles")
    public String rolesMain(Model model) {
        Iterable<Roles> roles = rolesRepository.findAll();
        model.addAttribute("roles", roles);
        return "roles-templates/roles-main";
    }

    @GetMapping("/roles/add")
    public String rolesAdd() {
        return "roles-templates/roles-add";
    }

    @PostMapping("/roles/add")
    public String rolesPostAdd(@RequestParam String name_role) {
        Roles roles = new Roles(name_role);
        rolesRepository.save(roles);
        return "redirect:/roles";
    }

    @GetMapping("/roles/{id}/edit")
    public String rolesEdit(@PathVariable(value = "id") long id, Model model) {
        if (!rolesRepository.existsById(id)) {
            return "redirect:/roles";
        }
        Roles roles = rolesRepository.findById(id).orElseThrow();
        model.addAttribute("roles", roles);
        return "roles-templates/roles-edit";
    }

    @PostMapping("/roles/{id}/edit")
    public String rolesPostEdit(@PathVariable(value = "id") long id, @RequestParam String name_role) {
        Roles roles = rolesRepository.findById(id).orElseThrow();
        roles.setName_role(name_role);
        rolesRepository.save(roles);
        return "redirect:/roles";
    }

    @PostMapping("/roles/{id}/remove")
    public String rolesPostDelete(@PathVariable(value = "id") long id) {
        Roles roles = rolesRepository.findById(id).orElseThrow();
        Iterable<People> peopleRepo = peopleRepository.findAll();
        for(People people : peopleRepo){
            if(roles.getId() == people.getRole_id().getId()){
                return "redirect:/roles";
            }
        }
        rolesRepository.delete(roles);
        return "redirect:/roles";
    }
}

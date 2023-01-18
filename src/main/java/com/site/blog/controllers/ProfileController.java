package com.site.blog.controllers;

import com.site.blog.entity.Users;
import com.site.blog.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.site.blog.StaticMethods.getCurrentUsername;

@Controller
public class ProfileController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/profile")
    public String profile(Model model){
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "profile-templates/profile-main";
    }

    @GetMapping("/profile/edit")
    public String profileEdit(Model model) {
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "profile-templates/profile-edit";
    }

    // TODO: fix bug editing with bindingResult
    @PostMapping("/profile/edit")
    public String profilePostEdit(@Valid @ModelAttribute("user") Users user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "profile-templates/profile-edit";
//        }
        Users editedUser = usersRepository.findById(getCurrentUsername()).orElseThrow();
        editedUser.setName(user.getName());
        editedUser.setSurname(user.getSurname());
        editedUser.setEmail(user.getEmail());
        usersRepository.save(editedUser);
        return "redirect:/profile";
    }

    @GetMapping("/profile/edit/username")
    public String profileEditUsername(Model model) {
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "profile-templates/profile-edit-username";
    }

    //TODO: do validation for username
    @PostMapping("/profile/edit/username")
    public String profilePostEditUsername(@RequestParam String username) {
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        usersRepository.changeUsername(user.getUsername(), username);
        return "redirect:/login?logout";
    }

    @GetMapping("/profile/edit/password")
    public String usersEditPassword(Model model) {
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "profile-templates/profile-edit-password";
    }

    //TODO: do validation for password
    @PostMapping("/profile/edit/password")
    public String usersPostEditPassword(@RequestParam String password) {
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        String codedPassword = "{bcrypt}" + new BCryptPasswordEncoder().encode(password);
        user.setPassword(codedPassword);
        usersRepository.save(user);
        return "redirect:/profile/edit";
    }

    @PostMapping("/profile/remove")
    public String usersPostDelete() {
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        usersRepository.delete(user);
        return "redirect:/login?logout";
    }
}

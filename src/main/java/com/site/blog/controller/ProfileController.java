package com.site.blog.controller;

import com.site.blog.service.UsersService;
import com.site.blog.validation.UserEditValidation;
import com.site.blog.validation.UsernameClass;
import com.site.blog.entity.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.site.blog.StaticMethods.getCurrentUsername;

@Controller
public class ProfileController {

    private final UsersService usersService;

    public ProfileController(UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Users user = usersService.findById(getCurrentUsername());
        model.addAttribute("user", user);
        return "profile-templates/profile-main";
    }

    @GetMapping("/profile/edit")
    public String profileEdit(Model model) {
        Users currentUser = usersService.findById(getCurrentUsername());
        UserEditValidation user = new UserEditValidation();
        user.setName(currentUser.getName());
        user.setSurname(currentUser.getSurname());
        user.setEmail(currentUser.getEmail());
        model.addAttribute("user", user);
        return "profile-templates/profile-edit";
    }

    @PostMapping("/profile/edit")
    public String profilePostEdit(@Valid @ModelAttribute("user") UserEditValidation user,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile-templates/profile-edit";
        }
        Users editedUser = usersService.findById(getCurrentUsername());
        editedUser.setName(user.getName());
        editedUser.setSurname(user.getSurname());
        editedUser.setEmail(user.getEmail());
        usersService.update(editedUser);
        return "redirect:/profile";
    }

    @GetMapping("/profile/edit/username")
    public String profileEditUsername(Model model) {
        UsernameClass username = new UsernameClass();
        model.addAttribute("username", username);
        return "profile-templates/profile-edit-username";
    }

    @PostMapping("/profile/edit/username")
    public String profilePostEditUsername(@Valid @ModelAttribute("username") UsernameClass username,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile-templates/profile-edit-username";
        }
        Users user = usersService.findById(getCurrentUsername());
        usersService.changeUsername(user.getUsername(), username.getUsername());
        return "redirect:/login?logout";
    }

    @GetMapping("/profile/edit/password")
    public String usersEditPassword() {
        return "profile-templates/profile-edit-password";
    }

    @PostMapping("/profile/edit/password")
    public String usersPostEditPassword(@RequestParam String password) {
        if (password.isBlank() || password.length() > 50) {
            return "profile-templates/profile-edit-password";
        }
        Users user = usersService.findById(getCurrentUsername());
        String codedPassword = "{bcrypt}" + new BCryptPasswordEncoder().encode(password);
        user.setPassword(codedPassword);
        usersService.update(user);
        return "redirect:/profile/edit";
    }

    @PostMapping("/profile/remove")
    public String usersPostDelete() {
        Users user = usersService.findById(getCurrentUsername());
        usersService.delete(user);
        return "redirect:/login?logout";
    }
}

package com.site.blog.controllers;

import com.site.blog.models.People;
import com.site.blog.models.Articles;
import com.site.blog.repo.PeopleRepository;
import com.site.blog.repo.ArticlesRepository;
import com.site.blog.repo.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;

@Controller
public class ArticlesController {

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping("/articles")
    public String articlesMain(Model model) {
        Iterable<Articles> posts = articlesRepository.findAll();
        model.addAttribute("posts", posts);
        return "articles-main";
    }

    @GetMapping("/articles/add")
    public String articlesAdd() {
        return "articles-add";
    }

    @PostMapping("/articles/add")
    public String articlesPostAdd(@RequestParam String title, @RequestParam String anons,
                                  @RequestParam String fullText, @RequestParam String nameAuthor,
                                  @RequestParam String surnameAuthor) {
        Iterable<People> peopleRepo = peopleRepository.findAll();

        for(People human : peopleRepo){
            if(human.getName_people().equals(nameAuthor) && human.getSurname_people().equals(surnameAuthor)){
                Articles post = new Articles(title, anons, fullText, new Date(), human);
                articlesRepository.save(post);
            }
        }

        return "redirect:/articles";
    }

    @GetMapping("/articles/{id}")
    public String articlesDetails(@PathVariable(value = "id") long id, Model model) {
        if (!articlesRepository.existsById(id)) {
            return "redirect:/articles";
        }

        Articles post = articlesRepository.findById(id).orElseThrow();

        model.addAttribute("post", post);

        return "articles-details";

    }

    @PostMapping("/articles/{id}")
    public String articlesPostDetails(@PathVariable(value = "id") long id) {
        Articles post = articlesRepository.findById(id).orElseThrow();

        int views = post.getViews();
        post.setViews(views+1);
        articlesRepository.save(post);

        return "redirect:/articles/{id}";
    }

    @GetMapping("/articles/{id}/edit")
    public String articlesEdit(@PathVariable(value = "id") long id, Model model) {
        if (!articlesRepository.existsById(id)) {
            return "redirect:/articles";
        }

        Articles post = articlesRepository.findById(id).orElseThrow();

        model.addAttribute("post", post);
        return "articles-edit";

    }

    @PostMapping("/articles/{id}/edit")
    public String articlesPostEdit(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String fullText) {
        Articles post = articlesRepository.findById(id).orElseThrow();

        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(fullText);

        articlesRepository.save(post);

        return "redirect:/articles";
    }

    @PostMapping("/articles/{id}/remove")
    public String articlesPostDelete(@PathVariable(value = "id") long id) {
        Articles post = articlesRepository.findById(id).orElseThrow();
        articlesRepository.delete(post);

        return "redirect:/articles";
    }
}

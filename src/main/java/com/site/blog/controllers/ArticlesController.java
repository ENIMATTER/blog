package com.site.blog.controllers;

import com.site.blog.models.People;
import com.site.blog.models.Articles;
import com.site.blog.repo.PeopleRepository;
import com.site.blog.repo.ArticlesRepository;
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

    @GetMapping("/articles")
    public String articlesMain(Model model) {
        Iterable<Articles> articles = articlesRepository.findAll();
        model.addAttribute("articles", articles);
        return "articles-templates/articles-main";
    }

    @GetMapping("/articles/add")
    public String articlesAdd() {
        return "articles-templates/articles-add";
    }

    @PostMapping("/articles/add")
    public String articlesPostAdd(@RequestParam String title, @RequestParam String anons,
                                  @RequestParam String full_text, @RequestParam String nameAuthor,
                                  @RequestParam String surnameAuthor) {
        Iterable<People> peopleRepo = peopleRepository.findAll();
        for(People human : peopleRepo){
            if(human.getName_people().equals(nameAuthor) && human.getSurname_people().equals(surnameAuthor)){
                Articles articles = new Articles(title, anons, full_text, new Date(), human);
                articlesRepository.save(articles);
            }
        }
        return "redirect:/articles";
    }

    @GetMapping("/articles/{id}")
    public String articlesDetails(@PathVariable(value = "id") long id, Model model) {
        if (!articlesRepository.existsById(id)) {
            return "redirect:/articles";
        }
        Articles articles = articlesRepository.findById(id).orElseThrow();
        model.addAttribute("articles", articles);
        return "articles-templates/articles-details";
    }

    @PostMapping("/articles/{id}")
    public String articlesPostDetails(@PathVariable(value = "id") long id) {
        Articles articles = articlesRepository.findById(id).orElseThrow();
        int views = articles.getViews();
        articles.setViews(views+1);
        articlesRepository.save(articles);
        return "redirect:/articles/{id}";
    }

    @GetMapping("/articles/{id}/edit")
    public String articlesEdit(@PathVariable(value = "id") long id, Model model) {
        if (!articlesRepository.existsById(id)) {
            return "redirect:/articles";
        }
        Articles articles = articlesRepository.findById(id).orElseThrow();
        model.addAttribute("articles", articles);
        return "articles-templates/articles-edit";
    }

    @PostMapping("/articles/{id}/edit")
    public String articlesPostEdit(@PathVariable(value = "id") long id, @RequestParam String title,
                                   @RequestParam String anons, @RequestParam String full_text,
                                   @RequestParam String nameAuthor, @RequestParam String surnameAuthor) {
        Articles articles = articlesRepository.findById(id).orElseThrow();
        Iterable<People> peopleRepo = peopleRepository.findAll();
        for(People human : peopleRepo){
            if(human.getName_people().equals(nameAuthor) && human.getSurname_people().equals(surnameAuthor)){
                articles.setTitle(title);
                articles.setAnons(anons);
                articles.setFull_text(full_text);
                articles.setPeople_id(human);
                articlesRepository.save(articles);
            }
        }
        return "redirect:/articles";
    }

    @PostMapping("/articles/{id}/remove")
    public String articlesPostDelete(@PathVariable(value = "id") long id) {
        Articles articles = articlesRepository.findById(id).orElseThrow();
        articlesRepository.delete(articles);
        return "redirect:/articles";
    }
}

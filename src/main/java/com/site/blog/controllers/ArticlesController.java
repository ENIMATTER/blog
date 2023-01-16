package com.site.blog.controllers;

import com.site.blog.models.Articles;
import com.site.blog.repo.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Date;

@Controller
public class ArticlesController {

    @Autowired
    private ArticlesRepository articlesRepository;

    @GetMapping("/articles")
    public String articlesMain(Model model) {
        Iterable<Articles> articles = articlesRepository.findAll();
        model.addAttribute("articles", articles);
        return "articles-templates/articles-main";
    }

    @GetMapping("/articles/add")
    public String articlesAdd(Model model) {
        Articles article = new Articles();
        model.addAttribute("article", article);
        return "articles-templates/articles-add";
    }

    @PostMapping("/articles/add")
    public String articlesPostAdd(@ModelAttribute("article") Articles article) {
        article.setDate_publication(new Date());
        articlesRepository.save(article);
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
        Articles article = articlesRepository.findById(id).orElseThrow();
        model.addAttribute("article", article);
        return "articles-templates/articles-edit";
    }

    @PostMapping("/articles/{id}/edit")
    public String articlesPostEdit(@PathVariable(value = "id") long id,
                                   @ModelAttribute("article") Articles article) {
        Articles editedArticle = articlesRepository.findById(id).orElseThrow();
        editedArticle.setTitle(article.getTitle());
        editedArticle.setAnons(article.getAnons());
        editedArticle.setFull_text(article.getFull_text());
        editedArticle.setUsers_id(article.getUsers_id());
        articlesRepository.save(editedArticle);
        return "redirect:/articles";
    }

    @PostMapping("/articles/{id}/remove")
    public String articlesPostDelete(@PathVariable(value = "id") long id) {
        articlesRepository.deleteById(id);
        return "redirect:/articles";
    }
}

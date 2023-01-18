package com.site.blog.controllers;

import com.site.blog.models.Articles;
import com.site.blog.models.Authorities;
import com.site.blog.models.Users;
import com.site.blog.repo.ArticlesRepository;
import com.site.blog.repo.AuthoritiesRepository;
import com.site.blog.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;

import static com.site.blog.StaticMethods.getCurrentUsername;

@Controller
public class ArticlesController {

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

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
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        article.setUsers_id(user);
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
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "articles-templates/articles-details";
    }

    @PostMapping("/articles/{id}")
    public String articlesPostDetails(@PathVariable(value = "id") long id) {
        Articles articles = articlesRepository.findById(id).orElseThrow();
        int views = articles.getViews();
        articles.setViews(views + 1);
        articlesRepository.save(articles);
        return "redirect:/articles/{id}";
    }

    @GetMapping("/articles/{id}/edit")
    public String articlesEdit(@PathVariable(value = "id") long id, Model model) {
        if (!articlesRepository.existsById(id)) {
            return "redirect:/articles";
        }
        Articles article = articlesRepository.findById(id).orElseThrow();
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        if (article.getUsers_id().equals(user)) {
            model.addAttribute("article", article);
            return "articles-templates/articles-edit";
        } else {
            List<Authorities> authoritiesList = authoritiesRepository.findAllByUsername(user);
            for (Authorities authority : authoritiesList) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    model.addAttribute("article", article);
                    return "articles-templates/articles-edit";
                }
            }
        }
        return "redirect:/articles";
    }

    @PostMapping("/articles/{id}/edit")
    public String articlesPostEdit(@PathVariable(value = "id") long id,
                                   @ModelAttribute("article") Articles article) {
        Articles editedArticle = articlesRepository.findById(id).orElseThrow();
        editedArticle.setTitle(article.getTitle());
        editedArticle.setAnons(article.getAnons());
        editedArticle.setFull_text(article.getFull_text());
        articlesRepository.save(editedArticle);
        return "redirect:/articles";
    }

    @PostMapping("/articles/{id}/remove")
    public String articlesPostDelete(@PathVariable(value = "id") long id) {
        Articles article = articlesRepository.findById(id).orElseThrow();
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        if (article.getUsers_id().equals(user)) {
            articlesRepository.deleteById(id);
        } else {
            List<Authorities> authoritiesList = authoritiesRepository.findAllByUsername(user);
            for (Authorities authority : authoritiesList) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    articlesRepository.deleteById(id);
                }
            }
        }
        return "redirect:/articles";
    }
}

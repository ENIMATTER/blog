package com.site.blog.controller;

import com.site.blog.entity.Articles;
import com.site.blog.entity.Authorities;
import com.site.blog.entity.Users;
import com.site.blog.repo.UsersRepository;
import com.site.blog.service.ArticlesService;
import com.site.blog.service.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static com.site.blog.StaticMethods.getCurrentUsername;

@Controller
public class ArticlesController {

    private final ArticlesService articlesService;

    private final AuthoritiesService authoritiesService;

    public ArticlesController(ArticlesService articlesService, AuthoritiesService authoritiesService){
        this.articlesService = articlesService;
        this.authoritiesService = authoritiesService;
    }

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/articles")
    public String articlesMain(Model model) {
        List<Articles> articles = articlesService.findAllByOrderByIdDesc();
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
    public String articlesPostAdd(@Valid @ModelAttribute("article") Articles article,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "articles-templates/articles-add";
        }
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        article.setUsers_id(user);
        article.setDate_publication(new Date());
        articlesService.add(article);
        return "redirect:/articles";
    }

    @GetMapping("/articles/{id}")
    public String articlesDetails(@PathVariable(value = "id") long id, Model model) {
        if (!articlesService.existsById(id)) {
            return "redirect:/articles";
        }
        Articles articles = articlesService.findById(id);
        model.addAttribute("articles", articles);
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "articles-templates/articles-details";
    }

    @PostMapping("/articles/{id}")
    public String articlesPostDetails(@PathVariable(value = "id") long id) {
        Articles articles = articlesService.findById(id);
        long views = articles.getViews();
        articles.setViews(views + 1);
        articlesService.update(articles);
        return "redirect:/articles/{id}";
    }

    @GetMapping("/articles/{id}/edit")
    public String articlesEdit(@PathVariable(value = "id") long id, Model model) {
        if (!articlesService.existsById(id)) {
            return "redirect:/articles";
        }
        Articles article = articlesService.findById(id);
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        if (article.getUsers_id().equals(user)) {
            model.addAttribute("article", article);
            return "articles-templates/articles-edit";
        } else {
            List<Authorities> authoritiesList = authoritiesService.findAllByUsername(user);
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
                                   @Valid @ModelAttribute("article") Articles article,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "articles-templates/articles-edit";
        }
        Articles editedArticle = articlesService.findById(id);
        editedArticle.setTitle(article.getTitle());
        editedArticle.setAnons(article.getAnons());
        editedArticle.setFull_text(article.getFull_text());
        articlesService.update(editedArticle);
        return "redirect:/articles";
    }

    @PostMapping("/articles/{id}/remove")
    public String articlesPostDelete(@PathVariable(value = "id") long id) {
        Articles article = articlesService.findById(id);
        Users user = usersRepository.findById(getCurrentUsername()).orElseThrow();
        if (article.getUsers_id().equals(user)) {
            articlesService.deleteById(id);
        } else {
            List<Authorities> authoritiesList = authoritiesService.findAllByUsername(user);
            for (Authorities authority : authoritiesList) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    articlesService.deleteById(id);
                }
            }
        }
        return "redirect:/articles";
    }
}

package com.site.blog.service;

import com.site.blog.entity.Articles;
import com.site.blog.repo.ArticlesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticlesService {
    private final ArticlesRepository articlesRepository;

    public ArticlesService(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public List<Articles> findAllByOrderByIdDesc(){
        return articlesRepository.findAllByOrderByIdDesc();
    }

    public Articles add(Articles articles){
        return articlesRepository.save(articles);
    }

    public Articles update(Articles articles){
        return articlesRepository.save(articles);
    }

    public void deleteById(Long id){
        articlesRepository.deleteById(id);
    }

    public List<Articles> findAllByTitle(String title){
        return articlesRepository.findAllByTitle(title);
    }

    public Articles findById(Long id){
        return articlesRepository.findById(id).orElseThrow();
    }

    public boolean existsById(Long id){
        return articlesRepository.existsById(id);
    }
}

package com.site.blog.repo;

import com.site.blog.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {
    List<Articles> findAllByOrderByIdDesc();
    List<Articles> findAllByTitle(String title);
}

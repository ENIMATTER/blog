package com.site.blog.repo;

import com.site.blog.models.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {

}

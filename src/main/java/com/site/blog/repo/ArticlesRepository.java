package com.site.blog.repo;

import com.site.blog.models.Articles;
import org.springframework.data.repository.CrudRepository;


public interface ArticlesRepository extends CrudRepository<Articles, Long> {

}

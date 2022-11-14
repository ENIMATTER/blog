package com.site.blog.repo;

import com.site.blog.models.Articles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRepository extends CrudRepository<Articles, Long> {

}

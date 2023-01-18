package com.site.blog.repo;

import com.site.blog.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ArticlesRepository extends JpaRepository<Articles, Long> {

}

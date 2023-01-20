package com.site.blog.repo;

import com.site.blog.entity.Authorities;
import com.site.blog.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
    List<Authorities> findAllByUsername(Users username);
}

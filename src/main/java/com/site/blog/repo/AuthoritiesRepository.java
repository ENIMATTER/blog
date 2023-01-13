package com.site.blog.repo;

import com.site.blog.models.Authorities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends CrudRepository<Authorities, Long> {

}

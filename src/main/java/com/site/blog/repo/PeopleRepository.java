package com.site.blog.repo;

import com.site.blog.models.People;
import org.springframework.data.repository.CrudRepository;

public interface PeopleRepository extends CrudRepository<People, Long> {

}

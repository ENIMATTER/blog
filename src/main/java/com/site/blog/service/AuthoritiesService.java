package com.site.blog.service;

import com.site.blog.entity.Authorities;
import com.site.blog.entity.Users;
import com.site.blog.repo.AuthoritiesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthoritiesService {
    private final AuthoritiesRepository authoritiesRepository;

    public AuthoritiesService(AuthoritiesRepository authoritiesRepository){
        this.authoritiesRepository = authoritiesRepository;
    }

    public List<Authorities> findAllByUsername(Users username){
        return authoritiesRepository.findAllByUsername(username);
    }

    public void saveAll(List<Authorities> authoritiesList){
        authoritiesRepository.saveAll(authoritiesList);
    }

    public void deleteAll(List<Authorities> authoritiesList){
        authoritiesRepository.deleteAll(authoritiesList);
    }
}

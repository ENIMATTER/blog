package com.site.blog.service;

import com.site.blog.entity.Users;
import com.site.blog.repo.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void changeUsername(String oldUsername, String newUsername) {
        usersRepository.changeUsername(oldUsername, newUsername);
    }

    public List<Users> findAllByOrderByUsernameAsc() {
        return usersRepository.findAllByOrderByUsernameAsc();
    }

    public Users update(Users users) {
        return usersRepository.save(users);
    }

    public void deleteById(String id) {
        usersRepository.deleteById(id);
    }

    public void delete(Users user) {
        usersRepository.delete(user);
    }

    public boolean existsById(String id) {
        return usersRepository.existsById(id);
    }

    public Users findById(String id) {
        return usersRepository.findById(id).orElseThrow();
    }
}

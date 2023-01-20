package com.site.blog.repo;

import com.site.blog.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    @Modifying
    @Query("UPDATE Users SET username = :newUsername WHERE username = :oldUsername")
    void changeUsername(@Param("oldUsername") String oldUsername, @Param("newUsername") String newUsername);

    List<Users> findAllByOrderByUsernameAsc();
}

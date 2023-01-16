package com.site.blog.repo;

import com.site.blog.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users, String> {
    @Modifying
    @Query("UPDATE Users SET username = :newUsername WHERE username = :oldUsername")
    void changeUsername(@Param("oldUsername") String oldUsername, @Param("newUsername") String newUsername);
}

package com.menglang.user.service.repository;

import com.menglang.user.service.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findFirstById(Long id);

    Optional<User> findFirstByUsernameAndStatus(String username, String status);

    List<User> findAllByIdIn(Set<Long> ids);

    @Query("""
            SELECT CASE WHEN COUNT(u)>0 THEN true ELSE false END FROM User u WHERE u.username=?1 AND u.id!=?2
            """)
    boolean existByUsername(String username,Long id);
}

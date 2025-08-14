package com.menglang.user.service.repository;

import com.menglang.user.service.entity.model.RefreshToken;
import com.menglang.user.service.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);

    void deleteAllByUserIn(List<User> users);

    Optional<RefreshToken> findByUser(User user);
}

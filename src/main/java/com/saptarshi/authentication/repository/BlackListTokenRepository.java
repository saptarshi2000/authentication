package com.saptarshi.authentication.repository;

import com.saptarshi.authentication.model.BlackListedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListTokenRepository extends JpaRepository<BlackListedToken,Long> {
    Optional<BlackListedToken> findByToken(String token);
}

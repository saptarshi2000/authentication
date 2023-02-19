package com.saptarshi.authentication.repository;

import com.saptarshi.authentication.model.User_;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User_,Long> {
    Optional<User_> findByEmail(String email);
}

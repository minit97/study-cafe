package com.example.studyCafe.api.auth.repository;

import com.example.studyCafe.api.auth.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByUsername(String username);
//    Boolean existsByUsername(String username);
//    Boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "authorities")    // lazy 로딩 대신 eager 로딩
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}

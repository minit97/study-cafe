package com.example.studyCafe.api.auth.repository;

import com.example.studyCafe.api.auth.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}

package com.example.studyCafe.api.board.repository;

import com.example.studyCafe.api.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
}

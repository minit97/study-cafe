package com.example.studyCafe.api.board.service;

import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.api.auth.service.UserService;
import com.example.studyCafe.api.board.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void clean() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 등록 - 성공")
    void boardCreate() {

    }

}
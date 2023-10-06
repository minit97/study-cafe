package com.example.studyCafe.api.auth.controller;

import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.api.board.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.local.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
class AuthControllerDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 가입")
    void signup() {

    }

    @Test
    @DisplayName("로그인")
    void login() {

    }

    @Test
    @DisplayName("본인 정보 조회")
    void myInfo() {

    }

    @Test
    @DisplayName("어드민 - 사용자 조회")
    void admin_userInfo() {

    }
}
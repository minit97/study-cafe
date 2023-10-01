package com.example.studyCafe.api.auth.controller;

import com.example.studyCafe.api.auth.dto.UserDto;
import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("/signup 회원가입 성공")
    void signupSuccess() throws Exception {
        // given
        UserDto request = UserDto.builder()
                .username("phm")
                .password("123")
                .nickname("박현민")
                .build();
        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/signup")
                .contentType(APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, userRepository.count());

        User user = userRepository.findAll().get(0);
        assertEquals("phm", user.getUsername());
        assertEquals("박현민", user.getNickname());
    }
}
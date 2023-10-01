package com.example.studyCafe.api.auth.service;

import com.example.studyCafe.api.auth.dto.UserDto;
import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입")
    void signup() {
        // given
        UserDto userDto = UserDto.builder()
                .username("phm")
                .password("123")
                .nickname("박현민")
                .build();

        // when
        userService.signup(userDto);

        // then
        assertEquals(1L, userRepository.count());
        User user = userRepository.findAll().get(0);
        assertEquals("phm", user.getUsername());
        assertEquals("박현민", user.getNickname());
    }

    @Test
    @DisplayName("관리자 전용 user 1명 조회")
    void admin_user_search() {
        // given
        UserDto userDto = UserDto.builder()
                .username("phm")
                .password("123")
                .nickname("박현민")
                .build();
        userService.signup(userDto);

        // when
        UserDto user = userService.getUserWithAuthorities("phm");

        // then
        assertNotNull(user);
        assertEquals(1L, userRepository.count());
        assertEquals("phm", user.getUsername());
        assertEquals("박현민", user.getNickname());
    }

}
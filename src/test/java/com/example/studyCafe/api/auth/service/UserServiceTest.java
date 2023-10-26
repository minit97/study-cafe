package com.example.studyCafe.api.auth.service;

import com.example.studyCafe.api.auth.dto.SignupDto;
import com.example.studyCafe.api.auth.dto.UserDto;
import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.testUtil.SecurityTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.studyCafe.api.auth.model.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityTestUtil securityTestUtil;


    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입 - 성공")
    void signupSuccess() {
        // given
        SignupDto signupDto = SignupDto.builder()
                .username("phm")
                .password("123")
                .nickname("박현민")
                .build();

        // when
        userService.signup(signupDto);

        // then
        assertEquals(1L, userRepository.count());
        User user = userRepository.findAll().get(0);
        assertEquals("phm", user.getUsername());
        assertEquals("박현민", user.getNickname());
    }

    @Test
    @DisplayName("본인 조회 - 성공")
    void myInfoSuccess() {
        // given
        User user = User.builder()
                .username("test")
                .password(securityTestUtil.passwordEncoder("1234"))
                .nickname("tester")
                .authorities(ROLE_USER)
                .build();
        userRepository.save(user);
        securityTestUtil.securityTokenSetting("test", "1234");

        // when
        UserDto resultUser = userService.getMyUserWithAuthorities();

        // then
        assertEquals("test", resultUser.getUsername());
        assertEquals("tester", resultUser.getNickname());
        System.out.println(resultUser.getAuthorityDtoList());
        boolean hasUserRole = resultUser.getAuthorityDtoList()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthorityName().equals(ROLE_USER));
        assertTrue(hasUserRole);
    }

    @Test
    @DisplayName("관리자 전용 user 1명 조회 - 성공")
    void admin_user_search() {
        // given
        User user = User.builder()
                .username("test")
                .password(securityTestUtil.passwordEncoder("1234"))
                .nickname("tester")
                .authorities(ROLE_USER)
                .build();
        userRepository.save(user);

        // when
        UserDto search = userService.getUserWithAuthorities("test");

        // then
        assertEquals("test", search.getUsername());
        assertEquals("tester", search.getNickname());
        boolean hasUserRole = search.getAuthorityDtoList()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthorityName().equals(ROLE_USER));
        assertTrue(hasUserRole);
    }

}
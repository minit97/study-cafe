package com.example.studyCafe.api.auth.service;

import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.testUtil.SecurityTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.example.studyCafe.api.auth.model.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private SecurityTestUtil securityTestUtil;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 - 성공")
    void loadUserByUsernameSuccess() {
        // given
        User user = User.builder()
                .username("test")
                .password(securityTestUtil.passwordEncoder("1234"))
                .nickname("tester")
                .authorities(ROLE_USER)
                .build();
        userRepository.save(user);

        // when
        UserDetails loginedUser = userDetailsService.loadUserByUsername("test");

        // then
        assertEquals("test", loginedUser.getUsername());

        // 권한 이름을 가져와서 비교
        boolean hasUserRole = loginedUser.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
        assertTrue(hasUserRole);
    }

    @Test
    @DisplayName("로그인 - 실패 : 해당 아이디 없음")
    void loadUserByUsernameFailNotUser() {
        // given
        User user = User.builder()
                .username("test")
                .password(securityTestUtil.passwordEncoder("1234"))
                .nickname("tester")
                .authorities(ROLE_USER)
                .build();
        userRepository.save(user);

        // expected
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("test1");
        });
    }

    @Test
    @DisplayName("로그인 - 실패 : 비밀번호틀림")
    void loadUserByUsernameFailNotPassword() {
        // given
        User user = User.builder()
                .username("test")
                .password(securityTestUtil.passwordEncoder("1234"))
                .nickname("tester")
                .authorities(ROLE_USER)
                .build();
        userRepository.save(user);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("test", "12345");

        // expected
        assertThrows(BadCredentialsException.class, () -> {
            authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        });
    }
}
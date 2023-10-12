package com.example.studyCafe.api.auth.controller;

import com.example.studyCafe.api.auth.dto.LoginDto;
import com.example.studyCafe.api.auth.dto.SignupDto;
import com.example.studyCafe.api.auth.model.Authority;
import com.example.studyCafe.api.auth.model.Role;
import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.config.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Set;

import static com.example.studyCafe.api.auth.model.Role.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 - AuthController")
    void login() throws Exception {
        // given
        User user = User.builder()
                .username("test")
                .password(passwordEncoder.encode("1234"))
                .nickname("tester")
                .authorities(ROLE_USER)
                .build();
        userRepository.save(user);

        LoginDto request = LoginDto.builder()
                .username("test")
                .password("1234")
                .build();
        String json = objectMapper.writeValueAsString(request);

        // expected
        this.mockMvc.perform(post("/api/authenticate")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("login",
                        requestFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("token").description("토큰")
                        )
                ));
    }

    @Test
    @DisplayName("회원 가입 - UserController")
    void signup() throws Exception {
        // given
        SignupDto request = SignupDto.builder()
                .username("test")
                .password("1234")
                .nickname("tester")
                .build();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/api/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("signup",
                        requestFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("nickname").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("nickname").description("비밀번호"),
                                fieldWithPath("authorityDtoList[].authorityName").description("권한")
                        )
                ));
    }

    @Test
    @WithMockUser(username = "test",roles = {"USER", "ADMIN"})
    @DisplayName("본인 정보 조회 - UserController")
    void myInfo() throws Exception {
        // given
        User user = User.builder()
                .username("test")
                .password(passwordEncoder.encode("1234"))
                .nickname("tester")
                .authorities(ROLE_USER)
                .build();
        userRepository.save(user);

        String jwtToken = jwtTokenGenerated("test", "1234");

        // expected
        mockMvc.perform(get("/api/user")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", jwtToken)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("myInfo",
                        requestHeaders(
                                headerWithName("Authorization").description("토큰")
                        ),
                        responseFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("nickname").description("비밀번호"),
                                fieldWithPath("authorityDtoList[].authorityName").description("권한")
                        )
                ));
    }

    @Test
    @DisplayName("어드민 - 사용자 조회  - UserController")
    void admin_userInfo() throws Exception {
        User user = User.builder()
                .username("test")
                .password(passwordEncoder.encode("1234"))
                .nickname("tester")
                .authorities(ROLE_ADMIN)
                .build();
        userRepository.save(user);
        User searcher = User.builder()
                .username("search")
                .password(passwordEncoder.encode("1234"))
                .nickname("searcher")
                .authorities(ROLE_USER)
                .build();
        userRepository.save(searcher);

        String jwtToken = jwtTokenGenerated("test", "1234");

        // expected
        mockMvc.perform(get("/api/user/{username}", "search")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", jwtToken)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("admin_userInfo",
                        requestHeaders(
                                headerWithName("Authorization").description("토큰")
                        ),
                        responseFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("nickname").description("비밀번호"),
                                fieldWithPath("authorityDtoList[].authorityName").description("권한")
                        )
                ));
    }

    private String jwtTokenGenerated(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        return jwt;
    }
}
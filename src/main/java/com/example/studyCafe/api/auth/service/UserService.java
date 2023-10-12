package com.example.studyCafe.api.auth.service;

import com.example.studyCafe.api.auth.dto.SignupDto;
import com.example.studyCafe.api.auth.dto.UserDto;
import com.example.studyCafe.api.auth.model.Authority;
import com.example.studyCafe.api.auth.model.Role;
import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.AuthorityRepository;
import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.exception.securityException.DuplicateMemberException;
import com.example.studyCafe.exception.securityException.NotFoundMemberException;
import com.example.studyCafe.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserDto signup(SignupDto signupDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(signupDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        User user = User.builder()
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .nickname(signupDto.getNickname())
                .build();
        Authority authority = Authority.builder()
                .authorityName(Role.ROLE_USER)
                .user(user)
                .build();
        authorityRepository.save(authority);

        return UserDto.from(userRepository.save(user));
    }


    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }
}

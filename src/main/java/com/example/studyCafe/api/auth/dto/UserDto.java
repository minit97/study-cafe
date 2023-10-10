package com.example.studyCafe.api.auth.dto;

import com.example.studyCafe.api.auth.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDto {

    private String username;
    private String nickname;
    private Set<AuthorityDto> authorityDtoSet;

    @Builder
    public UserDto(String username, String nickname, Set<AuthorityDto> authorityDtoSet) {
        this.username = username;
        this.nickname = nickname;
        this.authorityDtoSet = authorityDtoSet;
    }

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}

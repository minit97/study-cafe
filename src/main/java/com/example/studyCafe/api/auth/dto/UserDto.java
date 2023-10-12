package com.example.studyCafe.api.auth.dto;

import com.example.studyCafe.api.auth.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDto {

    private String username;
    private String nickname;
    private List<AuthorityDto> authorityDtoList;

    @Builder
    public UserDto(String username, String nickname, List<AuthorityDto> authorityDtoList) {
        this.username = username;
        this.nickname = nickname;
        this.authorityDtoList = authorityDtoList;
    }

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorityDtoList(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder()
                                                        .authorityName(authority.getAuthorityName())
                                                        .build())
                        .collect(Collectors.toList()))
                .build();
    }
}

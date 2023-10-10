package com.example.studyCafe.api.auth.dto;

import lombok.*;

@Getter
@Setter
public class TokenDto {

    private String token;

    @Builder
    public TokenDto(String token) {
        this.token = token;
    }
}


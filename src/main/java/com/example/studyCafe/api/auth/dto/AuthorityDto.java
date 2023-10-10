package com.example.studyCafe.api.auth.dto;

import lombok.*;

@Getter
@Setter
public class AuthorityDto {
    private String authorityName;

    @Builder
    public AuthorityDto(String authorityName) {
        this.authorityName = authorityName;
    }
}
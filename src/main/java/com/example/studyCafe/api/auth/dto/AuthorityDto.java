package com.example.studyCafe.api.auth.dto;

import com.example.studyCafe.api.auth.model.Role;
import lombok.*;

@Getter
@Setter
public class AuthorityDto {
    private Role authorityName;

    @Builder
    public AuthorityDto(Role authorityName) {
        this.authorityName = authorityName;
    }
}
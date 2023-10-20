package com.example.studyCafe.api.studycafe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class SpotAddRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String phone;

    @Builder
    public SpotAddRequest(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}

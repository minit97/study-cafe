package com.example.studyCafe.api.studycafe.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class SeatPickRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long seatId;

    @Builder
    public SeatPickRequest(Long userId, Long seatId) {
        this.userId = userId;
        this.seatId = seatId;
    }
}

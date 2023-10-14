package com.example.studyCafe.api.studycafe.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class SeatAddRequest {
    @NotNull
    private Long spotId;
    @NotNull
    private Integer seatNum;

    @Builder
    public SeatAddRequest(Long spotId, Integer seatNum) {
        this.spotId = spotId;
        this.seatNum = seatNum;
    }
}

package com.example.studyCafe.api.studycafe.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class SeatSearchRequest {
    private Boolean seatActive;

    @Builder
    public SeatSearchRequest(Boolean seatActive) {
        this.seatActive = seatActive;
    }
}

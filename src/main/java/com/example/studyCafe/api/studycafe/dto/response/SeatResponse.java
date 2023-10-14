package com.example.studyCafe.api.studycafe.dto.response;

import com.example.studyCafe.api.studycafe.model.Seat;
import lombok.Builder;
import lombok.Data;

@Data
public class SeatResponse {

    private Long seatId;
    private Integer seatNum;
    private Boolean seatActive;

    @Builder
    public SeatResponse(Long seatId, Integer seatNum, Boolean seatActive) {
        this.seatId = seatId;
        this.seatNum = seatNum;
        this.seatActive = seatActive;
    }

    public static SeatResponse from(Seat seat) {
        if(seat == null) {
            return null;
        }

        return SeatResponse.builder()
                .seatId(seat.getId())
                .seatNum(seat.getSeatNum())
                .seatActive(seat.getActive())
                .build();
    }
}

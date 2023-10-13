package com.example.studyCafe.api.studycafe.dto.response;

import com.example.studyCafe.api.board.dto.response.BoardResponseDto;
import com.example.studyCafe.api.board.model.Board;
import com.example.studyCafe.api.studycafe.model.Spot;
import lombok.Builder;
import lombok.Data;

@Data
public class SpotResponse {
    private String spotName;
    private String spotPhone;

    @Builder
    public SpotResponse(String spotName, String spotPhone) {
        this.spotName = spotName;
        this.spotPhone = spotPhone;
    }

    public static SpotResponse from(Spot spot) {
        if(spot == null) return null;

        return SpotResponse.builder()
                .spotName(spot.getName())
                .spotPhone(spot.getPhone())
                .build();
    }
}

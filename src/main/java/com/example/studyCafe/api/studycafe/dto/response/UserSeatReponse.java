package com.example.studyCafe.api.studycafe.dto.response;

import com.example.studyCafe.api.studycafe.model.UserSeat;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
public class UserSeatReponse {
    private String username;
    private String nickname;
    private Duration remainedTime;

    private Long seatId;
    private Integer seatNum;
    private Boolean seatActive;

    @Builder
    public UserSeatReponse(String username, String nickname, Duration remainedTime, Long seatId, Integer seatNum, Boolean seatActive) {
        this.username = username;
        this.nickname = nickname;
        this.remainedTime = remainedTime;
        this.seatId = seatId;
        this.seatNum = seatNum;
        this.seatActive = seatActive;
    }

    public static UserSeatReponse from(UserSeat userSeat) {
        if (userSeat == null) {
            return null;
        }

        return UserSeatReponse.builder()
                .username(userSeat.getUser().getUsername())
                .nickname(userSeat.getUser().getNickname())
                .remainedTime(userSeat.getUser().getRemainedTime())
                .seatId(userSeat.getSeat().getId())
                .seatNum(userSeat.getSeat().getSeatNum())
                .seatActive(userSeat.getSeat().getActive())
                .build();
    }
}

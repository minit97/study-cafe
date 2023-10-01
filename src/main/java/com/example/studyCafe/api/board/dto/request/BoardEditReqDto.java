package com.example.studyCafe.api.board.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardEditReqDto {
    private String title;
    private String content;

    @Builder
    public BoardEditReqDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

package com.example.studyCafe.api.board.dto.response;

import com.example.studyCafe.api.board.model.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public static BoardResponseDto from(Board board) {
        if(board == null) return null;

        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
//                .createAt(board.getCreateAt())
//                .updateAt(board.getUpdateAt())
                .build();
    }
}

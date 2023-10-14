package com.example.studyCafe.api.board.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.*;

@Getter
@Setter
public class BoardSearchReqDto {

    private static final int MAX_SIZE = 2000;

    private Integer page;
    private Integer size;

    @Builder
    public BoardSearchReqDto(Integer page, Integer size) {
        this.page = page == null ? 1 : page;
        this.size = size == null ? 10 : size;
    }

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }
}

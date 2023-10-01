package com.example.studyCafe.api.board.repository;

import com.example.studyCafe.api.board.dto.request.BoardSearchReqDto;
import com.example.studyCafe.api.board.model.Board;

import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> getList(BoardSearchReqDto boardSearchReqDto);
}

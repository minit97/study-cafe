package com.example.studyCafe.api.board.repository;

import com.example.studyCafe.api.board.dto.request.BoardSearchReqDto;
import com.example.studyCafe.api.board.model.Board;
import com.example.studyCafe.api.board.model.QBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.studyCafe.api.board.model.QBoard.*;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Board> getList(BoardSearchReqDto boardSearchReqDto) {
        return jpaQueryFactory.selectFrom(board)
                .limit(boardSearchReqDto.getSize())
                .offset(boardSearchReqDto.getOffset())
                .orderBy(board.postId.desc())
                .fetch();
    }
}

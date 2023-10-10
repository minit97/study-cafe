package com.example.studyCafe.api.board.controller;

import com.example.studyCafe.api.board.dto.request.BoardEditReqDto;
import com.example.studyCafe.api.board.dto.request.BoardSearchReqDto;
import com.example.studyCafe.api.board.dto.request.BoardWriteReqDto;
import com.example.studyCafe.api.board.dto.response.BoardResponseDto;
import com.example.studyCafe.api.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<BoardResponseDto> callBoardWrite(@RequestBody BoardWriteReqDto req) {
        return ResponseEntity.ok(boardService.boardWrite(req));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardResponseDto> callBoardGetOne(@PathVariable(name ="boardId") Long id) {
        return ResponseEntity.ok(boardService.boardOne(id));
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardResponseDto>> callBoardList(BoardSearchReqDto req) {
        return ResponseEntity.ok(boardService.boardList(req));
    }

    @PatchMapping("/board/{boardId}")
    public ResponseEntity<BoardResponseDto> callBoardEdit(@PathVariable Long boardId, @RequestBody BoardEditReqDto req) {
        return ResponseEntity.ok(boardService.boardEdit(boardId, req));
    }

    @DeleteMapping("/board/{boardId}")
    public void callBoardDel(@PathVariable Long boardId) {
        boardService.boardDelete(boardId);
    }
}

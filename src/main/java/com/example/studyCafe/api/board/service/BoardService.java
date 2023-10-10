package com.example.studyCafe.api.board.service;

import com.example.studyCafe.api.board.dto.request.BoardEditReqDto;
import com.example.studyCafe.api.board.dto.request.BoardSearchReqDto;
import com.example.studyCafe.api.board.dto.request.BoardWriteReqDto;
import com.example.studyCafe.api.board.dto.response.BoardResponseDto;
import com.example.studyCafe.api.board.model.Board;
import com.example.studyCafe.api.board.model.BoardEditor;
import com.example.studyCafe.api.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.studyCafe.api.board.model.Board.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponseDto boardWrite(BoardWriteReqDto req){
        Board board = builder()
                .title(req.getTitle())
                .content(req.getContent())
//                .createAt(LocalDateTime.now())
                .build();

        return BoardResponseDto.from(boardRepository.save(board));
    }

    @Transactional(readOnly = true)
    public BoardResponseDto boardOne(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());

        return BoardResponseDto.from(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> boardList(BoardSearchReqDto req) {
        return boardRepository.getList(req).stream()
                .map(BoardResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardResponseDto boardEdit(Long id, BoardEditReqDto boardEditReqDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        BoardEditor.BoardEditorBuilder boardEditorBuilder = board.toEditor();

        // 여기서 validchk 안하고 BoardEditor에서 validchk를 해도 괜찮을듯!
        if (boardEditReqDto.getTitle() != null) {
            boardEditorBuilder.title(boardEditReqDto.getTitle());
        }
        if (boardEditReqDto.getContent() != null) {
            boardEditorBuilder.content(boardEditReqDto.getContent());
        }

        board.edit(boardEditorBuilder.build());

        return BoardResponseDto.from(board);
    }

    public void boardDelete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        boardRepository.delete(board);
    }

}

package com.example.studyCafe.api.board.service;

import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.api.auth.service.UserService;
import com.example.studyCafe.api.board.dto.request.BoardEditReqDto;
import com.example.studyCafe.api.board.dto.request.BoardSearchReqDto;
import com.example.studyCafe.api.board.dto.request.BoardWriteReqDto;
import com.example.studyCafe.api.board.dto.response.BoardResponseDto;
import com.example.studyCafe.api.board.model.Board;
import com.example.studyCafe.api.board.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 등록 - 성공")
    void boardCreateSuccess() {
        // given
        BoardWriteReqDto request = BoardWriteReqDto.builder()
                .title("제목")
                .content("내용")
                .build();

        // when
        boardService.boardWrite(request);

        // then
        Board result = boardRepository.findAll().get(0);
        assertEquals("제목",result.getTitle());
        assertEquals("내용",result.getContent());
        assertNotNull(result.getCreatedDate());
        assertNotNull(result.getLastModifiedDate());
    }

    @Test
    @DisplayName("게시글 단건 조회 - 성공")
    void boardOneSuccess() {
        // given
        User user = User.builder()
                .username("test")
                .password("1234")
                .nickname("tester")
                .build();
        userRepository.save(user);
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .user(user)
                .build();
        boardRepository.save(board);
        
        BoardWriteReqDto request = BoardWriteReqDto.builder()
                .title("제목")
                .content("내용")
                .build();

        // when
        BoardResponseDto result = boardService.boardOne(board.getId());

        // then
        assertEquals("제목",result.getTitle());
        assertEquals("내용",result.getContent());
        assertNotNull(result.getCreateAt());
        assertNotNull(result.getUpdateAt());
    }

    @Test
    @DisplayName("게시글 전체 조회 - 성공")
    void boardListSuccess() {
        // given
        User user = User.builder()
                .username("test")
                .password("1234")
                .nickname("tester")
                .build();
        userRepository.save(user);
        for (int i = 1; i <= 10; i++) {
            Board board = Board.builder()
                    .title("제목"+ i)
                    .content("내용"+ i)
                    .user(user)
                    .build();
            boardRepository.save(board);
        }

        BoardSearchReqDto req = BoardSearchReqDto.builder()
                .page(1)
                .size(5)
                .build();

        // when
        List<BoardResponseDto> result = boardService.boardList(req);

        // then
        assertEquals(5,result.size());
        assertEquals("제목10",result.get(0).getTitle());
        assertEquals("내용10",result.get(0).getContent());
        assertNotNull(result.get(0).getCreateAt());
        assertNotNull(result.get(0).getUpdateAt());
    }

    @Test
    @DisplayName("게시글 수정 - 성공")
    void boardEditSuccess() {
        // given
        User user = User.builder()
                .username("test")
                .password("1234")
                .nickname("tester")
                .build();
        userRepository.save(user);
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .user(user)
                .build();
        boardRepository.save(board);

        BoardEditReqDto req = BoardEditReqDto.builder()
                .title("변경된 제목")
                .content("변경된 내용")
                .build();

        // when
        BoardResponseDto result = boardService.boardEdit(board.getId(), req);

        // then
        assertEquals("변경된 제목",result.getTitle());
        assertEquals("변경된 내용",result.getContent());
        assertNotNull(result.getCreateAt());
        assertNotNull(result.getUpdateAt());
    }

    @Test
    @DisplayName("게시글 삭제 - 성공")
    void boardDeleteSuccess() {
        // given
        User user = User.builder()
                .username("test")
                .password("1234")
                .nickname("tester")
                .build();
        userRepository.save(user);
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .user(user)
                .build();
        boardRepository.save(board);

        // when
        boardService.boardDelete(board.getId());

        // then
        List<Board> all = boardRepository.findAll();
        assertEquals(0L, all.size());
    }


}
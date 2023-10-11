package com.example.studyCafe.api.board.controller;

import com.example.studyCafe.api.board.dto.request.BoardEditReqDto;
import com.example.studyCafe.api.board.dto.request.BoardWriteReqDto;
import com.example.studyCafe.api.board.model.Board;
import com.example.studyCafe.api.board.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.local.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public class BoardControllerDocTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void clean() {
        boardRepository.deleteAll();
    }


    @Test
    @DisplayName("게시글 등록")
    void boardCreate() throws Exception {
        // given
        BoardWriteReqDto request = BoardWriteReqDto.builder()
                .title("제목")
                .content("내용")
                .build();
        String json = objectMapper.writeValueAsString(request);
        // expected
        this.mockMvc.perform(post("/api/board")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                                .content(json)
                            )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("board-create",
                        // request parameters exp
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용").optional()
                        ),
                        // response parameter exp
                        responseFields(
                                fieldWithPath("id").description("게시글 ID"),
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용"),
                                fieldWithPath("createAt").description("작성일자"),
                                fieldWithPath("updateAt").description("수정일자")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void boardGetOne() throws Exception {
        // given
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .build();
        boardRepository.save(board);

        // expected
        this.mockMvc.perform(get("/api/board/{boardId}", 1L)
                            .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("board-inquery",
                            pathParameters(
                                    parameterWithName("boardId").description("게시글 ID")
                            ),
                            responseFields(
                                    fieldWithPath("id").description("게시글 ID"),
                                    fieldWithPath("title").description("제목"),
                                    fieldWithPath("content").description("내용"),
                                    fieldWithPath("createAt").description("작성일자"),
                                    fieldWithPath("updateAt").description("수정일자")
                            )
                        ));
    }

    @Test
    @DisplayName("게시물 전체 조회")
    void boardList() throws Exception {
        // given
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .build();
        boardRepository.save(board);

        // expected
        this.mockMvc.perform(get("/api/boards?page={page}&size={size}",1,10)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("board-list",
                        queryParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("리스트 갯수")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("게시글 ID"),
                                fieldWithPath("[].title").description("제목"),
                                fieldWithPath("[].content").description("내용"),
                                fieldWithPath("[].createAt").description("작성일자"),
                                fieldWithPath("[].updateAt").description("수정일자")
                        )
                ));
    }

    @Test
    @DisplayName("게시물 수정")
    void boardEdit() throws Exception {
        // given
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .build();
        boardRepository.save(board);

        BoardEditReqDto request = BoardEditReqDto.builder()
                .title("변경된 제목")
                .content("변경된 내용")
                .build();
        String json = objectMapper.writeValueAsString(request);

        // expected
        this.mockMvc.perform(patch("/api/board/{boardId}", board.getId())
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON)
                            .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("board-upd",
                        pathParameters(
                                parameterWithName("boardId").description("게시글 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글 ID"),
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용"),
                                fieldWithPath("createAt").description("작성일자"),
                                fieldWithPath("updateAt").description("수정일자")
                        )
                ));
    }

    @Test
    @DisplayName("게시물 삭제")
    void boardDelete() throws Exception {
        // given
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .build();
        boardRepository.save(board);

        // expected
        this.mockMvc.perform(delete("/api/board/{boardId}", board.getId())
                        .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("board-del",
                        pathParameters(
                                parameterWithName("boardId").description("게시글 ID")
                        )
                ));
    }



}

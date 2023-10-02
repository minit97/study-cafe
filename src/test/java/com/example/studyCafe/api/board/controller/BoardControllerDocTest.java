package com.example.studyCafe.api.board.controller;

import com.example.studyCafe.api.board.dto.request.BoardWriteReqDto;
import com.example.studyCafe.api.board.model.Board;
import com.example.studyCafe.api.board.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
                .andDo(MockMvcResultHandlers.print())
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
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("board-inquery",
                            // request parameters exp
                            pathParameters(
                                    parameterWithName("boardId").description("게시글 ID")
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


}

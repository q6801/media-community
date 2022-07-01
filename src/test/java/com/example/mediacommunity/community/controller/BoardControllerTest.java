package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.community.controller.board.BoardController;
import com.example.mediacommunity.community.domain.board.BoardRequestDto;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import com.example.mediacommunity.community.service.board.BoardCategoryService;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardControllerTest extends BeforeTest{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BoardCategoryService boardCategoryService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BoardRepository boardRepository;

    private String categoryName = "testCategory";
    private long createdBoardId;

    @BeforeEach
    public void beforeTest() throws Exception {
        createCategory(boardCategoryService, categoryName);
        Member member = saveMember(memberService);
        createdBoardId = createBoard(boardRepository, categoryName, member);
    }

    @Test
    @DisplayName("board category 조회 성공 테스트")
    public void boards() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/boards/" + categoryName)
                .accept(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(BoardController.class))
                .andExpect(handler().methodName("boards"));
    }

//    @Test
//    @WithMockCustomUser
//    @DisplayName("정렬 순서 바꾼 boards 조회 성공")
//    public void board() throws Exception {
//        Map<String, String> input = new HashMap<>();
//        input.put("type", "heartCnt");
//        ResultActions result = mockMvc.perform(
//                post("/api/boards/" + categoryName)
//                .accept(MediaType.APPLICATION_JSON));
//        result.andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(handler().handlerType(BoardController.class))
//                .andExpect(handler().methodName("changeBoardsOrder"));
//
//    }

    @Test
    @WithMockCustomUser
    @DisplayName("board 조회 성공 테스트")
    public void successToChangeBoardsOrder() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/board/" + createdBoardId)
                        .accept(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(BoardController.class))
                .andExpect(handler().methodName("board"));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("board 작성 성공 테스트")
    public void createBoardWithTest() throws Exception {
        BoardRequestDto boardAddingDto =
                new BoardRequestDto("title", "content", categoryName, false);
        ResultActions result =  mockMvc.perform(
                post("/api/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardAddingDto))
        );
        result.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(BoardController.class))
                .andExpect(handler().methodName("addBoard"));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("board 수정, 삭제 성공 테스트")
    public void addAndEditAndDeleteBoard() throws Exception {
        BoardRequestDto boardEditingDto =
                new BoardRequestDto("newTitle", "newContent", categoryName, false);

        mockMvc.perform(
                put("/api/board/" + createdBoardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardEditingDto))
        ).andExpect(status().is2xxSuccessful());

        mockMvc.perform(
                delete("/api/board/" + createdBoardId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());
    }
}

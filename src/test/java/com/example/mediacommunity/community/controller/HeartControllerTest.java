package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.community.controller.board.HeartController;
import com.example.mediacommunity.community.domain.heart.HeartDto;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.service.board.BoardCategoryService;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.community.service.reply.ReplyService;
import com.example.mediacommunity.security.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeartControllerTest extends BeforeTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardCategoryService boardCategoryService;
    @Autowired
    private MemberService memberService;

    private String categoryName = "testCategory";
    private long createdBoardId;

    @BeforeEach
    public void beforeTest() throws Exception {
        createCategory(boardCategoryService, categoryName);
        Member member = saveMember(memberService);
        createdBoardId = createBoard(boardService, categoryName, member);
    }


    @Test
    @WithMockCustomUser
    @DisplayName("특정 게시판에 해당하는 heart들 조회 성공 테스트")
    public void hearts() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/board/" + createdBoardId + "/hearts")
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(HeartController.class))
                .andExpect(handler().methodName("hearts"));
    }

    @Test
    @WithMockCustomUser
    public void hitTheLikeButton() throws Exception {
        ResultActions result = mockMvc.perform(put("/api/board/" + createdBoardId + "/heart")
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(HeartController.class))
                .andExpect(handler().methodName("hitTheLikeButton"));
    }
}

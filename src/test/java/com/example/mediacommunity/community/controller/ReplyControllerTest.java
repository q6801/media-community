package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.community.controller.board.ReplyController;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.reply.ReplyRequestDto;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import com.example.mediacommunity.community.service.board.BoardCategoryService;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.community.service.reply.ReplyService;
import com.example.mediacommunity.security.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReplyControllerTest extends BeforeTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardCategoryService boardCategoryService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ReplyService replyService;

    private String categoryName = "testCategory";
    private long createdBoardId;
    private long createdReplyId;

    @BeforeEach
    public void beforeTest() throws Exception {
        createCategory(boardCategoryService, categoryName);
        Member member = saveMember(memberService);
        createdBoardId = createBoard(boardRepository, categoryName, member);
        createdReplyId = createReply(replyService, createdBoardId, member.getLoginId()).getId();
    }


    @Test
    @WithMockCustomUser
    public void addReply() throws Exception {
        ReplyRequestDto replyInputDto = new ReplyRequestDto("content");

        ResultActions result = mockMvc.perform(post("/api/board/" + createdBoardId + "/reply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(replyInputDto)));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ReplyController.class))
                .andExpect(handler().methodName("addReply"));
    }

    @Test
    @WithMockCustomUser
    public void findReplies() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/board/" + createdBoardId + "/replies")
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ReplyController.class))
                .andExpect(handler().methodName("replies"));
    }

    @Test
    @WithMockCustomUser
    public void putReply() throws Exception {
        ReplyRequestDto replyInputDto = new ReplyRequestDto("updated content");

        ResultActions result = mockMvc.perform(put("/api/reply/" + createdReplyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(replyInputDto)));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ReplyController.class))
                .andExpect(handler().methodName("putReply"));
    }

    @Test
    @WithMockCustomUser
    public void deleteReply() throws Exception {
        ResultActions result = mockMvc.perform(delete("/api/reply/" + createdReplyId)
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ReplyController.class))
                .andExpect(handler().methodName("deleteReply"));
    }
}

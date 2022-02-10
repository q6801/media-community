package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.community.domain.board.BoardAddingDto;
import com.example.mediacommunity.community.domain.heart.HeartInfoDto;
import com.example.mediacommunity.community.domain.member.SignUpDto;
import com.example.mediacommunity.community.domain.reply.ReplyInputDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationAPITest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void boards() throws Exception {
        mockMvc.perform(get("/boards").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void board() throws Exception {
        mockMvc.perform(get("/boardInfo/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("test1")
    public void addAndEditAndDeleteBoard() throws Exception {
        MvcResult mvcResult = createBoardWithTest();
        int boardIdx = getBoardIdx(mvcResult);
        BoardAddingDto boardEditingDto = new BoardAddingDto("newTitle", "newContent");

        mockMvc.perform(
                put("/board/" + boardIdx)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardEditingDto))
        ).andExpect(status().isCreated());

        mockMvc.perform(
                delete("/board/" + boardIdx)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    private MvcResult createBoardWithTest() throws Exception {
        BoardAddingDto boardAddingDto = new BoardAddingDto("title", "content");
        return mockMvc.perform(
                post("/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardAddingDto))
        ).andExpect(status().isCreated()).andReturn();
    }

    private int getBoardIdx(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String content = mvcResult.getResponse().getContentAsString();
        Map<String, Integer> result = objectMapper.readValue(content, Map.class);
        int boardIdx = result.get("boardIdx");
        return boardIdx;
    }

    @Test
    @WithUserDetails("test1")
    public void hearts() throws Exception {
        // board 생성 및 test
        MvcResult mvcResult = createBoardWithTest();
        int boardIdx = getBoardIdx(mvcResult);

        // get hearts test
        MvcResult hearts = mockMvc.perform(get("/boardInfo/" + boardIdx + "/hearts")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        HeartInfoDto heartInfoDto0 = objectMapper.readValue(
                hearts.getResponse().getContentAsString(), HeartInfoDto.class);

        assertThat(heartInfoDto0.getHeartsCnt()).isEqualTo(0);
        assertThat(heartInfoDto0.getPushed()).isEqualTo(false);

        // hitTheLikeButton test
        MvcResult hitTheLikeButton = mockMvc.perform(put("/board/" + boardIdx + "/heart")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        HeartInfoDto heartInfoDto1 = objectMapper.readValue(
                hitTheLikeButton.getResponse().getContentAsString(), HeartInfoDto.class);

        assertThat(heartInfoDto1.getHeartsCnt()).isEqualTo(1);
        assertThat(heartInfoDto1.getPushed()).isEqualTo(true);
    }

    @Test
    @WithUserDetails("test1")
    public void replyController() throws Exception {
        MvcResult board = createBoardWithTest();
        int boardIdx = getBoardIdx(board);
        ReplyInputDto replyInputDto = new ReplyInputDto("content");

        //  post reply
        mockMvc.perform(get("/boardInfo/" + boardIdx + "/replies")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        // get reply
        mockMvc.perform(post("/reply/" + boardIdx)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(replyInputDto))
        ).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("test1")
    public void memberController() throws Exception {

        // get Member info
        mockMvc.perform(get("/memberInfo")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        // put member
//        mockMvc.perform(put("/member")
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isOk())

        // sign up
        SignUpDto signUpDto = new SignUpDto("id", "pw", "nickname");
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDto))
        ).andExpect(status().isCreated());

        // delete member
        mockMvc.perform(delete("/member")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }
}

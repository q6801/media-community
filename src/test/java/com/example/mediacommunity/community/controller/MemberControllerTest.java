package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.community.controller.member.MemberController;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberEditDto;
import com.example.mediacommunity.community.domain.member.SignUpDto;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest extends BeforeTest{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void beforeTest() throws Exception {
        saveMember(memberService);
    }

    @Test
    @WithMockCustomUser
    public void memberController() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/member")
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("member"));
    }

    @Test
    @WithMockCustomUser
    public void editMember() throws Exception {
        MemberEditDto memberEditDto = new MemberEditDto(null, "hello world");
        ResultActions result = mockMvc.perform(put("/api/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberEditDto)));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("editMember"));
    }

    @Test
    @WithMockCustomUser
    public void signup() throws Exception {
        SignUpDto signUpDto = new SignUpDto("hello", "hello", "hello");
        ResultActions result = mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDto)));

        result.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("signUp"));
    }

    @Test
    @WithMockCustomUser
    public void signout() throws Exception {
        ResultActions result = mockMvc.perform(delete("/api/member")
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("signout"));
    }

    @Test
    public void login() throws Exception {
        ResultActions result = mockMvc.perform(post("/login")
                .param("loginId", "tester0")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        result.andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}

package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.community.controller.member.MemberController;
import com.example.mediacommunity.community.domain.member.MemberEditDto;
import com.example.mediacommunity.community.domain.member.SignUpDto;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
        List<MultipartFile> imageFiles = List.of(
                new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()),
                new MockMultipartFile("test2", "test2.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes())
        );

        ResultActions result = mockMvc.perform(
                multipart("/api/member")
//                        .file("file", imageFiles.get(0).getBytes()) // 2
                        .param("nickname", "newNickname")
                        .content("nickname")
                        .with(requestPostProcessor -> { // 3
                            requestPostProcessor.setMethod("PUT");
                            return requestPostProcessor;
                        })
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("nickname", "newNickname")
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("editMember"));
    }

    @Test
    @WithMockCustomUser
    public void signup() throws Exception {
        SignUpDto signUpDto = new SignUpDto("hello123", "hello1234", "hello1234","hello");
        ResultActions result = mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDto)));

        result.andDo(print())
                .andExpect(status().is3xxRedirection())
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

    @Test
    @DisplayName("로그인 실패 (아이디 없음)")
    public void failToLoginWithWrongId() throws Exception {
        ResultActions result = mockMvc.perform(post("/login")
                .param("loginId", "wrongTester")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error.errorCode", is(ExceptionEnum.USER_NOT_EXIST.getCode())))
        ;
    }

    @Test
    @DisplayName("로그인 실패 (잘못된 비밀번호)")
    public void failToLoginWithWrongPW() throws Exception {
        ResultActions result = mockMvc.perform(post("/login")
                .param("loginId", "tester0")
                .param("password", "password12")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error.errorCode", is(ExceptionEnum.BAD_PASSWORD.getCode())));
    }

//    @Test
//    @DisplayName("로그인 실패 (잘못된 provider)")
//    public void failToLoginWithWrongProvider() throws Exception {
//        ResultActions result = mockMvc.perform(post("/login")
//                .param("loginId", "tester0")
//                .param("password", "password12")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
//
//        result.andDo(print())
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("$.success", is(false)))
//                .andExpect(jsonPath("$.error.errorCode", is(ExceptionEnum.BAD_PASSWORD.getCode())));
//    }
}

//package com.example.mediacommunity.community.controller.home;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MockMvcBuilder;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.net.http.HttpHeaders;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class HomeControllerTest {
//
//    @InjectMocks
//    private HomeController homeController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void init() {
//        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
//    }
//
//    @Test
//    void notUser() throws Exception {
//        final ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.get("/")
//        );
//        MvcResult mvcResult = resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//        System.out.println("resultActions = " + mvcResult);
//
//    }
//
//}
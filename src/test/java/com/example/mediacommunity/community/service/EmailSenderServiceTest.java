//package com.example.mediacommunity.community.service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mail.SimpleMailMessage;
//
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
//
//class EmailSenderServiceTest {
//    @Autowired
//    private EmailSenderService emailSenderService;
//
//    @Test
//    void email() {
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo("q6801@naver.com");
//        simpleMailMessage.setSubject("회원가입 이메일 인증");
//        simpleMailMessage.setText("http://localhost:8080");
//        emailSenderService.sendMail(simpleMailMessage);
//    }
//}
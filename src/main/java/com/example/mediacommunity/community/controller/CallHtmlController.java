package com.example.mediacommunity.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CallHtmlController {
    @GetMapping("/")
    public String home() {
        return "axios/index";
    }

    @GetMapping("/comm")
    public String comm() {
        return "axios/comm";
    }

    @GetMapping("/addBoard")
    public String boardForm() {
        return "axios/addBoard";
    }

    @GetMapping("/board/{boardIdx}")
    public String board() {
        return "axios/board";
    }

    @GetMapping("/editBoard/{boardIdx}")
    public String boardEditForm() {
        return "axios/editBoard";
    }

    @GetMapping("/chatRooms")
    public String chatRooms() {
        return "axios/chatRooms";
    }

    @GetMapping("/chatRoom/{chatIdx}")
    public String chatRoom() {
        return "axios/chatRoom";
    }

    @GetMapping("/chat/addRoom")
    public String room() {
        return "axios/addChatRoom";
    }

    @GetMapping("/user")
    public String userInfo() {
        return "axios/userInfo";
    }


    @GetMapping("/wait-email")
    public String waitEmail() {
        return "axios/waitEmail";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "axios/loginForm";
    }

    @GetMapping("/signup")
    public String signUpForm() {
        return "axios/signUp";
    }

    @GetMapping("/editMember")
    public String editMemberForm() {
        return "axios/editMember";
    }
}

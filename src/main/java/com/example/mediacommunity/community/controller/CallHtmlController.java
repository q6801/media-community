package com.example.mediacommunity.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CallHtmlController {
    @GetMapping("/")
    public String home() {
        return "axios/index";
    }

    @GetMapping("/articles/{category}")
    public String comm() {
        return "axios/board/comm";
    }

    @GetMapping("/addBoard")
    public String boardForm() {
        return "axios/board/addBoard";
    }

    @GetMapping("/board/{boardIdx}")
    public String board() {
        return "axios/board/board";
    }

    @GetMapping("/editBoard/{boardIdx}")
    public String boardEditForm() {
        return "axios/board/editBoard";
    }

    @GetMapping("/streaming")
    public String chatRooms() {
        return "axios/streaming/streamingRooms";
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
        return "axios/user/userInfo";
    }

    @GetMapping("/confirm-email")
    public String waitEmail() {
        return "axios/confirmEmail";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "axios/user/loginForm";
    }

    @GetMapping("/signup")
    public String signUpForm() {
        return "axios/user/signUp";
    }

    @GetMapping("/editMember")
    public String editMemberForm() {
        return "axios/user/editMember";
    }

    @GetMapping("/{username}/stream-manager")
    public String presenter() {
        return "axios/streaming/presenter";
    }

    @GetMapping("/{username}/broadcast")
    public String viewer() {
        return "axios/streaming/viewer";
    }
}

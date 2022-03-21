package com.example.mediacommunity.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CallHtmlController {
    // index
    @GetMapping("/")
    public String home() {
        return "axios/index";
    }

    //board
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

    // room
    @GetMapping("/streaming")
    public String streamRooms() {
        return "axios/streaming/streamingRooms";
    }

    @GetMapping("/chat")
    public String chatRooms() {
        return "axios/chat/chatRooms";
    }

    @GetMapping("/chatRoom/{chatIdx}")
    public String chatRoom() {
        return "axios/chatRoom";
    }

    @GetMapping("/stream-manager/{username}")
    public String presenter() {
        return "axios/streaming/presenter";
    }

    @GetMapping("/broadcast/{username}")
    public String viewer() {
        return "axios/streaming/viewer";
    }

    @GetMapping("/chat/addRoom")
    public String room() {
        return "axios/addChatRoom";
    }

    // user
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
}

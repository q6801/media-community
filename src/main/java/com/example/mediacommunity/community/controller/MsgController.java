package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.community.domain.MsgRoom;
import com.example.mediacommunity.community.service.MsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MsgController {

    private final MsgService msgService;

    @GetMapping("/chat")
    public String createRoom(@RequestParam String name, Model model) {
        // TODO : name is Summoner Name
        model.addAttribute("msgRoom", msgService.createRoom(name));
        return "fuck";
    }

    @GetMapping("/fucklkkkk")
    public List<MsgRoom> findAllRoom() {
        return msgService.findAllRoom();
    }
}

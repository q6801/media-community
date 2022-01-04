package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.community.domain.MsgRoom;
import com.example.mediacommunity.community.domain.MsgRoomDto;
import com.example.mediacommunity.community.domain.board.BoardAddingDto;
import com.example.mediacommunity.community.service.MsgService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MsgRoomController {

    private final MsgService msgService;

    @GetMapping("/chat/rooms")
    public String rooms(Model model) {
        List<MsgRoom> allRoom = msgService.findAllRoom();
        model.addAttribute("rooms", allRoom);
        return "chat/chatRooms";
    }

    @GetMapping("/chat/add")
    public String addForm(Model model) {
        model.addAttribute("RoomDto", new MsgRoomDto());
        return "chat/addChatRoom";
    }

    @PostMapping("/chat/add")
    public String addRoom(@RequestParam String roomName) {
        MsgRoom room = msgService.createRoom(roomName);
        return "redirect:/chat/rooms";
    }

    @GetMapping("/room/{roomId}")
    public String roomEnter(Model model, @PathVariable UUID roomId, @AuthenticationPrincipal UserInfo userInfo) {
        MsgRoom msgRoom = msgService.findById(roomId);
        if (msgRoom == null) {
            throw new RuntimeException("msgRoom이 없다!!!!!!!!!!!!!!!!!!!!");
        }
        model.addAttribute("roomName", msgRoom.getRoomName());
        model.addAttribute("roomId", roomId);
        return "chat/chat";
    }
}

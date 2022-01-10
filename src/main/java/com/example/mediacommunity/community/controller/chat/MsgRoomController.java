package com.example.mediacommunity.community.controller.chat;

import com.example.mediacommunity.community.domain.chat.MsgInfoDto;
import com.example.mediacommunity.community.domain.chat.MsgRoom;
import com.example.mediacommunity.community.domain.chat.MsgRoomDto;
import com.example.mediacommunity.community.service.MsgService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MsgRoomController {

    private final MsgService msgService;

    @GetMapping("/chat/rooms")
    public List<MsgRoom> rooms(Model model) {
        List<MsgRoom> allRoom = msgService.findAllRoom();
        return allRoom;
    }

    @GetMapping("/chat/room/{roomId}")
    public MsgInfoDto roomEnter(@PathVariable UUID roomId, @AuthenticationPrincipal UserInfo userInfo) {
        MsgRoom msgRoom = msgService.findById(roomId);
        return new MsgInfoDto(msgRoom.getId(), msgRoom.getRoomName());
    }

    @PostMapping("/chat/room")
    public ResponseEntity<?> addRoom(@RequestBody Map<String, String> roomMap) {
        msgService.createRoom(roomMap.get("roomName"));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/chat/add")
    public String addForm(Model model) {
        model.addAttribute("RoomDto", new MsgRoomDto());
        return "chat/addChatRoom";
    }
}

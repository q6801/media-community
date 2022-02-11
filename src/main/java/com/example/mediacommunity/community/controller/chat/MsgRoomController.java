package com.example.mediacommunity.community.controller.chat;

import com.example.mediacommunity.community.domain.chat.MsgInfoDto;
import com.example.mediacommunity.community.domain.chat.Room;
import com.example.mediacommunity.community.domain.chat.RoomType;
import com.example.mediacommunity.community.service.RoomServiceImpl;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MsgRoomController {
    private final RoomServiceImpl roomService;

    @GetMapping("/chat-rooms")
    public List<Room> getRoom() {
        List<Room> rooms = roomService.findRoomsByType(RoomType.CHAT);
        return rooms;
    }

    @GetMapping("/chat-room/{roomId}")
    public MsgInfoDto roomEnter(@PathVariable UUID roomId) {
        Room room = roomService.findById(roomId);
        return new MsgInfoDto(room.getId(), room.getRoomName());
    }

    @PostMapping("chat-room")
    public ResponseEntity<?> addChattingRoom(@RequestBody Map<String, String> roomMap,
                                              @AuthenticationPrincipal UserInfo userInfo) {
        roomService.createRoom(roomMap.get("roomName"), userInfo.getName(), RoomType.CHAT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/chat-room")
    public ResponseEntity<?> deleteRoom(@AuthenticationPrincipal UserInfo userInfo) {
        roomService.deleteRoom(userInfo.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

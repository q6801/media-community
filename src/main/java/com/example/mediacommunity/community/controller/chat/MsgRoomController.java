package com.example.mediacommunity.community.controller.chat;

import com.example.mediacommunity.community.domain.chat.MsgInfoDto;
import com.example.mediacommunity.community.domain.chat.Room;
import com.example.mediacommunity.community.domain.chat.RoomType;
import com.example.mediacommunity.community.service.Pagination;
import com.example.mediacommunity.community.service.RoomServiceImpl;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MsgRoomController {
    private final RoomServiceImpl roomService;
    private final Pagination pagination;

    @GetMapping("/chat-rooms")
    public Map<String, Object> getRooms(@RequestParam(defaultValue = "1") int page) {
        int totalRoomsNum = roomService.getTotalRoomsNum(RoomType.CHAT);
        pagination.pageInfo(page, totalRoomsNum);
        List<Room> rooms = roomService.findRoomsByType(RoomType.CHAT);

        Map<String, Object> map = new HashMap<>();
        map.put("rooms", rooms);
        map.put("pagination", pagination);
        return map;
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
        roomService.deleteRoom(userInfo.getName(), RoomType.CHAT);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

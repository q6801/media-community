package com.example.mediacommunity.community.controller.streaming;

import com.example.mediacommunity.community.domain.chat.MsgInfoDto;
import com.example.mediacommunity.community.domain.chat.Room;
import com.example.mediacommunity.community.domain.chat.RoomType;
import com.example.mediacommunity.community.service.StreamingService;
import com.example.mediacommunity.community.service.RoomServiceImpl;
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
@RequestMapping("api")
public class StreamingRoomController {

    private final RoomServiceImpl roomServiceImpl;
    private final StreamingService streamingService;

    @GetMapping("/streaming-rooms")
    public List<Room> rooms(Model model) {
        List<Room> allRoom = roomServiceImpl.findRoomsByType(RoomType.STREAMING);
        return allRoom;
    }

    @GetMapping("/streaming-rooms/{roomId}")
    public MsgInfoDto roomEnter(@PathVariable UUID roomId) {
        Room room = roomServiceImpl.findById(roomId);
        return new MsgInfoDto(room.getId(), room.getRoomName());
    }

    @PostMapping("streaming-room")
    public ResponseEntity<?> addStreamingRoom(@RequestBody Map<String, String> roomMap,
                                     @AuthenticationPrincipal UserInfo userInfo) {
        roomServiceImpl.findByPresenter(userInfo.getName(), RoomType.STREAMING)
                .ifPresent(room -> roomServiceImpl.deleteRoom(userInfo.getName(), RoomType.STREAMING));
        roomServiceImpl.createRoom(roomMap.get("roomName"), userInfo.getName(), RoomType.STREAMING);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/streaming-room")
    public ResponseEntity<?> deleteRoom( @AuthenticationPrincipal UserInfo userInfo) {
        roomServiceImpl.deleteRoom(userInfo.getName(), RoomType.STREAMING);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

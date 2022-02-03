package com.example.mediacommunity.community.controller.chat;

import com.example.mediacommunity.community.domain.chat.MsgInfoDto;
import com.example.mediacommunity.community.domain.chat.StreamingRoom;
import com.example.mediacommunity.community.service.StreamingRoomService;
import com.example.mediacommunity.config.CallHandler;
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
public class StreamingRoomController {

    private final StreamingRoomService streamingRoomService;
    private final CallHandler callHandler;

    @GetMapping("/streaming-rooms")
    public List<StreamingRoom> rooms(Model model) {
        List<StreamingRoom> allRoom = streamingRoomService.findAllRoom();
        return allRoom;
    }

    @GetMapping("/streaming-rooms/{roomId}")
    public MsgInfoDto roomEnter(@PathVariable UUID roomId, @AuthenticationPrincipal UserInfo userInfo) {
        StreamingRoom streamingRoom = streamingRoomService.findById(roomId);
        return new MsgInfoDto(streamingRoom.getId(), streamingRoom.getRoomName());
    }

    @PostMapping("streaming-room")
    public ResponseEntity<?> addStreamingRoom(@RequestBody Map<String, String> roomMap,
                                     @AuthenticationPrincipal UserInfo userInfo) {
        streamingRoomService.findByPresenter(userInfo.getName())
                .ifPresent(room -> streamingRoomService.deleteRoom(userInfo.getName()));
        streamingRoomService.createRoom(roomMap.get("roomName"), userInfo.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/streaming-room")
    public ResponseEntity<?> deleteRoom( @AuthenticationPrincipal UserInfo userInfo) {
        streamingRoomService.deleteRoom(userInfo.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

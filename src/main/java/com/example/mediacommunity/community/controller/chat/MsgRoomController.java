//package com.example.mediacommunity.community.controller.chat;
//
//import com.example.mediacommunity.community.domain.chat.MsgInfoDto;
//import com.example.mediacommunity.community.domain.chat.MsgRoomsDto;
//import com.example.mediacommunity.community.domain.chat.Room;
//import com.example.mediacommunity.community.domain.chat.RoomType;
//import com.example.mediacommunity.community.service.Pagination;
//import com.example.mediacommunity.community.service.RoomServiceImpl;
//import com.example.mediacommunity.security.userInfo.UserInfo;
//import com.example.mediacommunity.utils.ApiResult;
//import com.example.mediacommunity.utils.ApiUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("api")
//public class MsgRoomController {
//    private final RoomServiceImpl roomService;
//    private final Pagination pagination;
//
//    @GetMapping("/chat-rooms")
//    public ApiResult<MsgRoomsDto> getRooms(@RequestParam(defaultValue = "1") int page) {
//        int totalRoomsNum = roomService.getTotalRoomsNum(RoomType.CHAT);
//        pagination.pageInfo(page, totalRoomsNum);
//        List<Room> rooms = roomService.findRoomsByType(RoomType.CHAT);
//        return ApiUtils.success(new MsgRoomsDto(rooms, pagination));
//    }
//
//    @GetMapping("/chat-room/{roomId}")
//    public ApiResult<MsgInfoDto> roomEnter(@PathVariable UUID roomId) {
//        Room room = roomService.findById(roomId);
//        return ApiUtils.success(new MsgInfoDto(room.getId(), room.getRoomName()));
//    }
//
//    @PostMapping("chat-room")
//    public ApiResult<?> addChattingRoom(@RequestBody Map<String, String> roomMap,
//                                             @AuthenticationPrincipal UserInfo userInfo) {
//        roomService.createRoom(roomMap.get("roomName"), userInfo.getName(), RoomType.CHAT);
//        return ApiUtils.success(null);
//    }
//
//    @DeleteMapping("/chat-room")
//    public ApiResult<Object> deleteRoom(@AuthenticationPrincipal UserInfo userInfo) {
//        roomService.deleteRoom(userInfo.getName(), RoomType.CHAT);
//        return ApiUtils.success(null);
//    }
//}

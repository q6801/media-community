package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.chat.Room;
import com.example.mediacommunity.community.domain.chat.RoomType;
import com.example.mediacommunity.community.repository.streaming.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl {
    private final RoomRepository roomRepository;

    public Room createRoom(String roomName, String memberId, RoomType roomType) {
        Room room = Room.builder()
                .roomName(roomName)
                .presenter(memberId)
                .roomType(roomType)
                .build();
        roomRepository.saveRoom(room);
        return room;
    }

    public Room findById(UUID roomId) {
        return roomRepository.findByRoomId(roomId);
    }

    public Optional<Room> findByPresenter(String username, RoomType roomType) {
        return roomRepository.findByPresenter(username, roomType);
    }

    public List<Room> findRoomsByType(RoomType roomType) {
        return roomRepository.findRoomsByType(roomType);
    }

    public int getTotalRoomsNum(RoomType roomType) {
        return roomRepository.getTotalRoomsNum(roomType);
    }

    public void deleteRoom(String username, RoomType roomType) {
        findByPresenter(username, roomType)
                .ifPresent((room) -> roomRepository.deleteRoom(room));
    }
}

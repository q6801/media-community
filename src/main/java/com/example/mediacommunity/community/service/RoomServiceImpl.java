package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.chat.Room;
import com.example.mediacommunity.community.domain.chat.RoomType;
import com.example.mediacommunity.community.repository.streaming.StreamingRoomRepository;
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
    private final StreamingRoomRepository streamingRoomRepository;

    public Room createRoom(String roomName, String memberId, RoomType roomType) {
        Room room = Room.builder()
                .roomName(roomName)
                .presenter(memberId)
                .roomType(roomType)
                .build();
        streamingRoomRepository.saveRoom(room);
        return room;
    }

    public Room findById(UUID roomId) {
        return streamingRoomRepository.findByRoomId(roomId);
    }

    public Optional<Room> findByPresenter(String username) {
        return streamingRoomRepository.findByPresenter(username);
    }

    public List<Room> findRoomsByType(RoomType roomType) {
        return streamingRoomRepository.findRoomsByType(roomType);
    }

    public void deleteRoom(String username) {
        findByPresenter(username)
                .ifPresent((room) -> streamingRoomRepository.deleteRoom(room));
    }
}

package com.example.mediacommunity.community.service;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.BlankExistException;
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
        if (roomName.isBlank()) {
            throw new BlankExistException(ExceptionEnum.BLANK_EXIST);
        }
        Room room = Room.builder()
                .roomName(roomName)
                .presenter(memberId)
                .roomType(roomType)
                .build();
        roomRepository.saveRoom(room);
        return room;
    }

    @Transactional(readOnly = true)
    public Room findById(UUID roomId) {
        return roomRepository.findByRoomId(roomId);
    }

    @Transactional(readOnly = true)
    public Optional<Room> findByPresenter(String username, RoomType roomType) {
        return roomRepository.findByPresenter(username, roomType);
    }

    @Transactional(readOnly = true)
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

package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.chat.StreamingRoom;
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
public class StreamingRoomService {
    private final StreamingRoomRepository streamingRoomRepository;

    public StreamingRoom createRoom(String roomName, String memberId) {
        StreamingRoom streamingRoom = StreamingRoom.builder()
                .roomName(roomName)
                .presenter(memberId)
                .build();
        streamingRoomRepository.saveRoom(streamingRoom);
        return streamingRoom;
    }

    public StreamingRoom findById(UUID roomId) {
        return streamingRoomRepository.findByRoomId(roomId);
    }

    public Optional<StreamingRoom> findByPresenter(String username) {
        return streamingRoomRepository.findByPresenter(username);
    }

    public List<StreamingRoom> findAllRoom() {
        return streamingRoomRepository.findAllRoom();
    }

    public void deleteRoom(String username) {
        findByPresenter(username)
                .ifPresent((room) -> streamingRoomRepository.deleteRoom(room));
    }
}

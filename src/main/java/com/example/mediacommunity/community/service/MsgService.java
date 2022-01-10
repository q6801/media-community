package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.chat.MsgRoom;
import com.example.mediacommunity.community.repository.MsgRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MsgService {
    private final ObjectMapper objectMapper;
    private final MsgRoomRepository msgRoomRepository;

    public MsgRoom createRoom(String name) {
        MsgRoom msgRoom = MsgRoom.builder()
                .roomName(name)
                .build();
        msgRoomRepository.saveMsgRoom(msgRoom);
        return msgRoom;
    }

    public MsgRoom findById(UUID roomId) {
        return msgRoomRepository.findByRoomId(roomId);
    }

    public List<MsgRoom> findAllRoom() {
        return msgRoomRepository.findAllRoom();
    }

}

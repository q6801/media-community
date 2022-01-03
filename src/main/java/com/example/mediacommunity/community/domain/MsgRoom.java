package com.example.mediacommunity.community.domain;

import com.example.mediacommunity.community.service.MsgService;
import lombok.Builder;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@ToString
public class MsgRoom {
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public MsgRoom(String roomId) {
        this.roomId = roomId;
    }

    public void handleActions(WebSocketSession session, Message message, MsgService msgService) {
        if (message.getMessageType().equals(Message.Messagetype.ENTER)) {
            sessions.add(session);
            message.setMessage(message.getSender() + "님이 입장");
        }
        sendMessage(message, msgService);
    }

    public <T> void sendMessage(T message, MsgService messageService) {
        sessions.parallelStream().forEach(session->messageService.sendMessage(session, message));
    }
}

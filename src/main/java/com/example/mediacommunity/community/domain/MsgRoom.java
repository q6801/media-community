package com.example.mediacommunity.community.domain;

import com.example.mediacommunity.community.service.MsgService;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MsgRoom {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String roomName;

//
//    private Set<WebSocketSession> sessions = new HashSet<>();
//
//    @Builder
//    public MsgRoom(String roomId) {
//        this.roomId = roomId;
//    }
//
//    public void handleActions(WebSocketSession session, Message message, MsgService msgService) {
//        if (message.getMessageType().equals(Message.Messagetype.ENTER)) {
//            sessions.add(session);
//            message.setMessage(message.getSender() + "님이 입장");
//        }
//        sendMessage(message, msgService);
//    }
//
//    public <T> void sendMessage(T message, MsgService messageService) {
//        sessions.parallelStream().forEach(session->messageService.sendMessage(session, message));
//    }
}

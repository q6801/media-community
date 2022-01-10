package com.example.mediacommunity.community.controller.chat;

import com.example.mediacommunity.community.domain.chat.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MsgController {

    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/comm/message")
    public void message(Message message) {
        sendingOperations.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping("/enter")
    public void enter(Message message) {
        message.setMessage(message.getWriter() + "이 입장했습니다.");
        sendingOperations.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}

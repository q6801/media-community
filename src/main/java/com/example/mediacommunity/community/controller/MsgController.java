package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.community.domain.Message;
import com.example.mediacommunity.community.domain.MsgRoom;
import com.example.mediacommunity.community.service.MsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

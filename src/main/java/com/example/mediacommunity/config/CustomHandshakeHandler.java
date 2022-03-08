package com.example.mediacommunity.config;

import com.example.mediacommunity.security.userInfo.StompPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        System.out.println("request = " + request.getPrincipal());

        StompPrincipal stompPrincipal;
        String userUUID = UUID.randomUUID().toString();
        if (request.getPrincipal() != null) {
            stompPrincipal = new StompPrincipal(userUUID, request.getPrincipal().getName());
        } else {
            stompPrincipal = new StompPrincipal(userUUID);
        }
        return stompPrincipal;
    }
}

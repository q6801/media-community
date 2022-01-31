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
        String username = null;
        if (request.getPrincipal() == null) {
            username = UUID.randomUUID().toString();
        } else {
            username = request.getPrincipal().getName();
        }
//        return super.determineUser(request, wsHandler, attributes);
        return new StompPrincipal(username);
    }
}

package com.example.mediacommunity.community.repository;

import com.example.mediacommunity.community.domain.UserSession;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class StreamingUserRepository {
    private UserSession presenter;
    private String sessionId;
    private ConcurrentHashMap<String, UserSession> viewers = new ConcurrentHashMap<>();

    public StreamingUserRepository(UserSession presenter, String sessionId) {
        this.presenter = presenter;
        this.sessionId = sessionId;
    }
}

package com.example.mediacommunity.community.repository.streaming;

import com.example.mediacommunity.community.domain.UserSession;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class StreamingUserRepository {
    private UserSession presenter;
    private String presenterUUID;
    private String sessionId;
    private ConcurrentHashMap<String, UserSession> viewers = new ConcurrentHashMap<>();
    // 여기서 sessionId와 UUID는 완전 다름
    // sessionId는 webRTC를 연결하는 세션으로 한번 연결한 presenter의 세션ID는 유지됨
    // UUID는 매 번 peerConnection할때마다 생김
    // 즉, 두 개가 같게 되면 방송을 튼 유저가 다른 방송 시청을 누르면 두 개의 브라우저에 같은 요청이 전해짐

    public StreamingUserRepository(UserSession presenter, String sessionId, String presenterUUID) {
        this.presenter = presenter;
        this.sessionId = sessionId;
        this.presenterUUID = presenterUUID;
    }
}

package com.example.mediacommunity.community.domain;

import com.example.mediacommunity.security.userInfo.UserInfo;
import com.google.gson.JsonObject;
import org.kurento.client.IceCandidate;
import org.kurento.client.WebRtcEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Kurento Java Tutorial - Per-user session state.
 */
public class UserSession {
    private static final Logger log = LoggerFactory.getLogger(UserSession.class);

    private WebRtcEndpoint webRtcEndpoint;


    public WebRtcEndpoint getWebRtcEndpoint() {
        return webRtcEndpoint;
    }

    public void setWebRtcEndpoint(WebRtcEndpoint webRtcEndpoint) {
        this.webRtcEndpoint = webRtcEndpoint;
    }

    public void addCandidate(IceCandidate candidate) {
        webRtcEndpoint.addIceCandidate(candidate);
    }
}
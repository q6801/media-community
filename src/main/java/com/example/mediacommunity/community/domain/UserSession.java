package com.example.mediacommunity.community.domain;

import org.kurento.client.IceCandidate;
import org.kurento.client.WebRtcEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
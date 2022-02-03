/*
 * (C) Copyright 2014 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.mediacommunity.config;

import com.example.mediacommunity.community.domain.UserSession;
import com.example.mediacommunity.community.repository.StreamingUserRepository;
import com.example.mediacommunity.security.userInfo.StompPrincipal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.kurento.client.*;
import org.kurento.jsonrpc.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Protocol handler for 1 to N video call communication.
 *
 * @author Boni Garcia (bgarcia@gsyc.es)
 * @since 5.0.0
 */
@Service
@RequiredArgsConstructor
public class CallHandler {

    private static final Logger log = LoggerFactory.getLogger(CallHandler.class);
    private static final Gson gson = new GsonBuilder().create();

    private final ConcurrentHashMap<String, StreamingUserRepository> presenters = new ConcurrentHashMap<>();
    private final SimpMessageSendingOperations sendingOperations;

    private MediaPipeline pipeline;
    private final KurentoClient kurento;

    public void handleErrorResponse(Throwable throwable, String responseId, String presenterName, SimpMessageHeaderAccessor accessor)
            throws IOException {
        stop(presenterName, accessor);
        log.error(throwable.getMessage(), throwable);
        JsonObject response = new JsonObject();
        response.addProperty("id", responseId);
        response.addProperty("response", "rejected");
        response.addProperty("message", throwable.getMessage());
//        sendingOperations.convertAndSend("/sub/message/" + responseId, response.toString());
        sendingOperations.convertAndSendToUser(((StompPrincipal)accessor.getUser()).getUserUUID(), "/sub/message/" + responseId,
                response.toString());
    }

    public synchronized void presenter(String message, SimpMessageHeaderAccessor accessor) throws IOException {
        StompPrincipal userInfo = (StompPrincipal) accessor.getUser();
        String sessionId = accessor.getSessionId();

        UserSession presenterUserSession = new UserSession();
        presenters.put(userInfo.getMemberId(), new StreamingUserRepository(
                presenterUserSession, sessionId, userInfo.getUserUUID()));
        pipeline = kurento.createMediaPipeline();

        presenterUserSession.setWebRtcEndpoint(new WebRtcEndpoint.Builder(pipeline).build());
        WebRtcEndpoint presenterWebRtc = presenterUserSession.getWebRtcEndpoint();
        presenterWebRtc.setStunServerAddress("stun.l.google.com");
        presenterWebRtc.setStunServerPort(19302);
        presenterWebRtc.setTurnUrl("q6801:turndkagh@34.64.213.114:3478");

        presenterWebRtc.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {
            @Override
            public void onEvent(IceCandidateFoundEvent event) {
                JsonObject response = new JsonObject();
                response.addProperty("id", "iceCandidate");
                response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
                synchronized (userInfo) {
//                    session.sendMessage(new TextMessage(response.toString()));
//                    sendingOperations.convertAndSend("/sub/message/iceCandidate/p", response.toString());
                    sendingOperations.convertAndSendToUser(userInfo.getUserUUID(), "/sub/message/iceCandidate",
                            response.toString());
                }
            }
        });

        JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
        String sdpOffer = jsonMessage.getAsJsonPrimitive("sdpOffer").getAsString();
        String sdpAnswer = presenterWebRtc.processOffer(sdpOffer);

        JsonObject response = new JsonObject();
        response.addProperty("id", "presenterResponse");
        response.addProperty("response", "accepted");
        response.addProperty("sdpAnswer", sdpAnswer);

        synchronized (userInfo) {
            sendingOperations.convertAndSendToUser(userInfo.getUserUUID(), "/sub/message/presenterResponse",
                    response.toString());
        }
        presenterWebRtc.gatherCandidates();
    }

    public synchronized void viewer(String message, String presenterName, SimpMessageHeaderAccessor accessor) throws IOException {
        System.out.println("viewer에 진입");
        StompPrincipal userInfo = (StompPrincipal) accessor.getUser();
        String sessionId = accessor.getSessionId();
        StreamingUserRepository streamingUserRepository = presenters.get(presenterName);

        if (streamingUserRepository == null || streamingUserRepository.getPresenter().getWebRtcEndpoint() == null) {
            JsonObject response = new JsonObject();
            response.addProperty("id", "viewerResponse");
            response.addProperty("response", "rejected");
            response.addProperty("message",
                    "아직 방송을 키지 않았습니다. Try again later ...");
            sendingOperations.convertAndSendToUser(userInfo.getUserUUID(),
                    "/sub/message/viewerResponse", response.toString());
        } else {
            if (presenters.get(presenterName).getViewers().containsKey(sessionId)) {
                JsonObject response = new JsonObject();
                response.addProperty("id", "viewerResponse");
                response.addProperty("response", "rejected");
                response.addProperty("message", "You are already viewing in this session. "
                        + "Use a different browser to add additional viewers.");
                sendingOperations.convertAndSendToUser(userInfo.getUserUUID(),
                        "/sub/message/viewerResponse", response.toString());
                return;
            }
            UserSession presenterUserSession = streamingUserRepository.getPresenter();
            UserSession viewer = new UserSession();
//            viewers.put(session.getId(), viewer);
            // ????????????????????
            // 결국 같은 sessionId면 같은 게 나오는데
            presenters.get(presenterName).getViewers().put(sessionId, viewer);

            WebRtcEndpoint nextWebRtc = new WebRtcEndpoint.Builder(pipeline).build();
            nextWebRtc.setStunServerAddress("stun.l.google.com");
            nextWebRtc.setStunServerPort(19302);
            nextWebRtc.setTurnUrl("q6801:turndkagh@34.64.213.114:3478");

            nextWebRtc.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {
                @Override
                public void onEvent(IceCandidateFoundEvent event) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "iceCandidate");
                    response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));

                    synchronized (this) {
                        sendingOperations.convertAndSendToUser(userInfo.getUserUUID(),
                                "/sub/message/iceCandidate", response.toString(),
                                accessor.getMessageHeaders());
                    }
                }
            });

            viewer.setWebRtcEndpoint(nextWebRtc);
            presenterUserSession.getWebRtcEndpoint().connect(nextWebRtc);

            JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
            String sdpOffer = jsonMessage.getAsJsonPrimitive("sdpOffer").getAsString();
            String sdpAnswer = nextWebRtc.processOffer(sdpOffer);

            JsonObject response = new JsonObject();
            response.addProperty("id", "viewerResponse");
            response.addProperty("response", "accepted");
            response.addProperty("sdpAnswer", sdpAnswer);

            System.out.println("viewer " + accessor.getUser().getName() + ", " + sessionId);
            synchronized (this) {
                sendingOperations.convertAndSendToUser(userInfo.getUserUUID(),
                        "/sub/message/viewerResponse", response.toString());
            }
            nextWebRtc.gatherCandidates();
        }
    }

    public synchronized void iceCandidate(String message, String presenterName,
                                          String sessionId, StompPrincipal userInfo) {
        JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
        JsonObject candidate = jsonMessage.get("candidate").getAsJsonObject();

        StreamingUserRepository streamingUserRepository = presenters.get(presenterName);

        UserSession user = null;
        if (userInfo != null && presenterName.equals(userInfo.getMemberId()) &&
                userInfo.getUserUUID().equals(streamingUserRepository.getPresenterUUID()) &&
                streamingUserRepository.getSessionId().equals(sessionId)) {
            user = streamingUserRepository.getPresenter();
        } else if(streamingUserRepository.getViewers() != null) {
            user = streamingUserRepository.getViewers().get(sessionId);
        }

        if (user != null) {
            IceCandidate cand = new IceCandidate(candidate.get("candidate").getAsString(), candidate.get("sdpMid")
                            .getAsString(), candidate.get("sdpMLineIndex").getAsInt());
            user.addCandidate(cand);
        }
    }

    public void stop(String presenterName, SimpMessageHeaderAccessor accessor) throws IOException {
        StompPrincipal userInfo = (StompPrincipal) accessor.getUser();
        String sessionId = accessor.getSessionId();

        String username = null;
        if (userInfo!=null) {
            username = userInfo.getMemberId();
        }

        StreamingUserRepository streamingUserRepository = presenters.get(presenterName);
        if (streamingUserRepository != null) {
            ConcurrentHashMap<String, UserSession> viewers = streamingUserRepository.getViewers();
            if (streamingUserRepository.getPresenter() != null && presenterName.equals(username)) {     // 스트리머가 방송 끔
                for (String viewerSessionId : viewers.keySet()) {
                    viewers.remove(viewerSessionId);
                }
            } else if (viewers.containsKey(sessionId)) {                                                // 시청자의 방송 종료
                if (viewers.get(sessionId).getWebRtcEndpoint() != null) {
                    viewers.get(sessionId).getWebRtcEndpoint().release();
                }
                viewers.remove(sessionId);
            }
        }
        JsonObject response = new JsonObject();
        System.out.println("presenters = " + presenters);
        synchronized (this) {
            sendingOperations.convertAndSendToUser(userInfo.getUserUUID(), "/sub/message/stopCommunication",
                    response.toString());
        }

//            if (pipeline != null) {
//                pipeline.release();
//            }
//            pipeline = null;
    }
}

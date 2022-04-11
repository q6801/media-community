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

package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.UserSession;
import com.example.mediacommunity.community.repository.streaming.StreamingUserRepository;
import com.example.mediacommunity.security.userInfo.StompPrincipal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.kurento.client.*;
import org.kurento.jsonrpc.JsonUtils;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class StreamingService {
    private static final Gson gson = new GsonBuilder().create();

    private final ConcurrentHashMap<String, StreamingUserRepository> presenters = new ConcurrentHashMap<>();
    private final SimpMessageSendingOperations sendingOperations;
    private final MediaPipeline pipeline;


    public void handleErrorResponse(Throwable throwable, String responseId, String presenterName, SimpMessageHeaderAccessor accessor)
            throws IOException {
        stop(presenterName, accessor);
        sendErrorToUser((StompPrincipal)accessor.getUser(), responseId,
                throwable.getMessage(), responseId);
    }

    public synchronized void presenter(String message, SimpMessageHeaderAccessor accessor) throws IOException {
        StompPrincipal userInfo = (StompPrincipal) accessor.getUser();
        String sessionId = accessor.getSessionId();

        UserSession presenterUserSession = new UserSession();
        presenters.put(userInfo.getMemberId(), new StreamingUserRepository(
                presenterUserSession, sessionId, userInfo.getUserUUID()));

        presenterUserSession.setWebRtcEndpoint(new WebRtcEndpoint.Builder(pipeline).build());
        WebRtcEndpoint presenterWebRtc = presenterUserSession.getWebRtcEndpoint();
        setStunAndTurnServer(presenterWebRtc);
        addIceCandidateFoundListnerInPresenter(userInfo, presenterWebRtc);

        JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
        String sdpOffer = jsonMessage.getAsJsonPrimitive("sdpOffer").getAsString();
        String sdpAnswer = presenterWebRtc.processOffer(sdpOffer);

        JsonObject response = setReponseDataToSend("presenterResponse", "accepted",
                "sdpAnswer", sdpAnswer);

        synchronized (userInfo) {
            sendingOperations.convertAndSendToUser(userInfo.getUserUUID(), "/sub/message/presenterResponse",
                    response.toString());
        }
        presenterWebRtc.gatherCandidates();
    }

    private void setStunAndTurnServer(WebRtcEndpoint presenterWebRtc) {
        presenterWebRtc.setStunServerAddress("stun.l.google.com");
        presenterWebRtc.setStunServerPort(19302);
        presenterWebRtc.setTurnUrl("q6801:turndkagh@34.64.213.114:3478");
    }

    private void addIceCandidateFoundListnerInPresenter(StompPrincipal userInfo, WebRtcEndpoint presenterWebRtc) {
        presenterWebRtc.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {
            @Override
            public void onEvent(IceCandidateFoundEvent event) {
                JsonObject response = new JsonObject();
                response.addProperty("id", "iceCandidate");
                response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
                synchronized (userInfo) {
                    sendingOperations.convertAndSendToUser(userInfo.getUserUUID(), "/sub/message/iceCandidate",
                            response.toString());
                }
            }
        });
    }

    public synchronized void viewer(String message, String presenterName, SimpMessageHeaderAccessor accessor) throws IOException {
        System.out.println("viewer에 진입");
        StompPrincipal userInfo = (StompPrincipal) accessor.getUser();
        String sessionId = accessor.getSessionId();
        StreamingUserRepository streamingUserRepository = presenters.get(presenterName);

        if (streamingUserRepository == null || streamingUserRepository.getPresenter().getWebRtcEndpoint() == null) {
            sendErrorToUser(userInfo, "viewerResponse",
                    "아직 방송을 키지 않았습니다. Try again later ...", "viewerResponse");
        } else if (presenters.get(presenterName).getViewers().containsKey(sessionId)) {
            sendErrorToUser(userInfo, "viewerResponse", "You are already viewing in this session. "
                    + "Use a different browser to add additional viewers.", "viewerResponse");
            return;
        } else {
            UserSession presenterUserSession = streamingUserRepository.getPresenter();
            UserSession viewer = new UserSession();
            presenters.get(presenterName).getViewers().put(sessionId, viewer);

            WebRtcEndpoint nextWebRtc = new WebRtcEndpoint.Builder(pipeline).build();
            setStunAndTurnServer(nextWebRtc);
            addIceCandidateFoundListnerInViewer(accessor, userInfo, nextWebRtc);
            viewer.setWebRtcEndpoint(nextWebRtc);
            presenterUserSession.getWebRtcEndpoint().connect(nextWebRtc);

            JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
            String sdpOffer = jsonMessage.getAsJsonPrimitive("sdpOffer").getAsString();
            String sdpAnswer = nextWebRtc.processOffer(sdpOffer);

            JsonObject response = setReponseDataToSend("viewerResponse", "accepted",
                    "sdpAnswer", sdpAnswer);

            synchronized (this) {
                sendingOperations.convertAndSendToUser(userInfo.getUserUUID(),
                        "/sub/message/viewerResponse", response.toString());
            }
            nextWebRtc.gatherCandidates();
        }
    }

    private JsonObject setReponseDataToSend(String viewerResponse, String status, String messageKey, String messageValue) {
        JsonObject response = new JsonObject();
        response.addProperty("id", viewerResponse);
        response.addProperty("response", status);
        response.addProperty(messageKey, messageValue);
        return response;
    }

    private void sendErrorToUser(StompPrincipal userInfo, String viewerResponse, String message, String destination) {
        JsonObject response = setReponseDataToSend(viewerResponse, "rejected", "message", message);
        sendingOperations.convertAndSendToUser(userInfo.getUserUUID(),
                "/sub/message/" + destination, response.toString());
    }

    private void addIceCandidateFoundListnerInViewer(SimpMessageHeaderAccessor accessor, StompPrincipal userInfo, WebRtcEndpoint nextWebRtc) {
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
    }

    public synchronized void iceCandidate(String message, String presenterName,
                                          String sessionId, StompPrincipal userInfo) {
        JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
        JsonObject candidate = jsonMessage.get("candidate").getAsJsonObject();

        StreamingUserRepository streamingUserRepository = presenters.get(presenterName);

        UserSession user = null;
        if (isPresenter(presenterName, sessionId, userInfo, streamingUserRepository)) {
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

    private boolean isPresenter(String presenterName, String sessionId, StompPrincipal userInfo, StreamingUserRepository streamingUserRepository) {
        return userInfo != null && presenterName.equals(userInfo.getMemberId()) &&
                userInfo.getUserUUID().equals(streamingUserRepository.getPresenterUUID()) &&
                streamingUserRepository.getSessionId().equals(sessionId);
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

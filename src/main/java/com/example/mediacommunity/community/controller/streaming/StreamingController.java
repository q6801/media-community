//package com.example.mediacommunity.community.controller.streaming;
//
//import com.example.mediacommunity.community.service.StreamingService;
//import com.example.mediacommunity.security.userInfo.StompPrincipal;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.stereotype.Controller;
//
//import java.io.IOException;
//import java.security.Principal;
//
//@Controller
//@RequiredArgsConstructor
//public class StreamingController {
//    private final StreamingService streamingService;
//
//    @MessageMapping("/streaming/presenter/{presenter}")
//    public void message(String message, SimpMessageHeaderAccessor accessor) throws Exception {
//        Principal user = accessor.getUser();
//        try {
//            streamingService.presenter(message, accessor);
//        } catch (Throwable t) {
//            streamingService.handleErrorResponse(t, "presenterResponse", user.getName(), accessor);
//        }
//    }
//
//    @MessageMapping("/streaming/viewer/{presenter}")
//    public void messageView(String message, SimpMessageHeaderAccessor accessor,
//                            @DestinationVariable("presenter") String presenterName) throws IOException {
//        try {
//            streamingService.viewer(message, presenterName, accessor);
//        } catch (Throwable t) {
//            streamingService.handleErrorResponse(t, "viewerResponse", presenterName, accessor);
//        }
//    }
//
//    @MessageMapping("/streaming/onIceCandidate/{presenter}")
//    public void messageCandidate(String message, SimpMessageHeaderAccessor accessor,
//                                 @DestinationVariable("presenter") String presenterName) throws IOException {
//        streamingService.iceCandidate(message, presenterName, accessor.getSessionId(), (StompPrincipal) accessor.getUser());
//    }
//
//    @MessageMapping("/streaming/stop/{presenter}")
//    public void messageStop(String message, SimpMessageHeaderAccessor accessor,
//                            @DestinationVariable("presenter") String presenterName) throws IOException {
//        streamingService.stop(presenterName, accessor);
//    }
//}

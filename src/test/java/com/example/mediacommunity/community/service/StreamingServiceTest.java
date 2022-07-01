//package com.example.mediacommunity.community.service;
//
//import com.example.mediacommunity.community.domain.member.Member;
//import com.example.mediacommunity.security.userInfo.StompPrincipal;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.kurento.client.MediaPipeline;
//import org.kurento.client.WebRtcEndpoint;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.mock;
//
//@ExtendWith(MockitoExtension.class)
//public class StreamingServiceTest {
//    @Mock
//    MediaPipeline pipeline;
//    @Mock
//    WebRtcEndpoint webRtcEndpoint;
//    @Mock
//    WebRtcEndpoint.Builder builder;
//
//    @InjectMocks
//    StreamingService streamingService;
//
////    @Test
////    @DisplayName("hg")
////    public void test() throws IOException {
////        Member member = getStubMemberList().get(0);
////        SimpMessageHeaderAccessor simpMessageHeaderAccessor = SimpMessageHeaderAccessor.create();
////        simpMessageHeaderAccessor.setUser(new StompPrincipal(UUID.randomUUID().toString(), member.getLoginId()));
//////        given(WebRtcEndpoint.Builder(pipeline)).willReturn();
////        WebRtcEndpoint.Builder mockBuilder = mock(new WebRtcEndpoint.Builder(pipeline).getClass());
////        given(mockBuilder.build()).willReturn(webRtcEndpoint);
////        streamingService.presenter("hello", simpMessageHeaderAccessor);
////        WebRtcEndpoint.Builder builder = new WebRtcEndpoint.Builder(pipeline);
////    }
//
//    private List<Member> getStubMemberList() {
//        return Arrays.asList(
//                Member.builder()
//                        .loginId("test121")
//                        .nickname("test1!")
//                        .password("password0").build(),
//                Member.builder()
//                        .loginId("test1232")
//                        .nickname("test!")
//                        .password("password1").build()
//        );
//    }
//}

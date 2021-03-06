package com.example.mediacommunity.config;

import lombok.RequiredArgsConstructor;
import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws/chat", "/ws/stream")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new CustomHandshakeHandler())
//                .withSockJS()
        ;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }

//    @Bean
//    public MediaPipeline mediaPipeline() {
//        return kurentoClient().createMediaPipeline();
//    }
//
//    @Bean
//    public KurentoClient kurentoClient() {
//        return KurentoClient.create();
//    }
}

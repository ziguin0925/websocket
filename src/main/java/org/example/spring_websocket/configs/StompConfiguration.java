package org.example.spring_websocket.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker // stomp기능 활성화
@Configuration
public class StompConfiguration implements WebSocketMessageBrokerConfigurer {

    /**
     * websocket 클라이언트가 어떠한 경로로 서버로 접근해야하는지에 대한 endpoint 지정.
     * */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chats");
    }

    /**
     * 메세지 브로커의 역할을 하기 위해서 클라이언트에서 메세지를 발행하고,
     * 클라이언트는 브로커로부터 메세지를 받기위해 구독을 신청하는 로직.
     * */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub"); //메세지 구독 신청.
    }
}

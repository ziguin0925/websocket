package org.example.spring_websocket.handlers;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// WebSocketHandler 인터페이스를 상속할 경우 구현 할 게 많음.
@Slf4j
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {


    final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    /**
     * websocket 클라이언트가 서버로 연결을 한 이후에 실행되는 코드
     * */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} connected", session.getId());
        this.sessionMap.put(session.getId(), session);
    }

    /**
     * websocket 클라이언트에서 메세지가 왔을때 그 메세지들을 처리하는 로직
     * */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("{} send: {}", session.getId(), message.getPayload());

        // 소켓 세션에 담겨있는 메세지를 다른 클라이언트에게 전송
        this.sessionMap.values().forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 서버에 접속해있는 클라이언트가 연결을 끊었을 때 처리하는 로직
     * */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} disconnected", session.getId());
        this.sessionMap.remove(session.getId());
    }
}

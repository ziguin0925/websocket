package org.example.spring_websocket.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class StompChatController {

    /**
     *  "/pub/chat"으로 발행 된 메세지들이 들어옴.(StompConfiguration)
     * */
    @MessageMapping("/chats")
    @SendTo("/sub/chats") // 해당 경로를 구독하는 클라이언트 들에게 메세지를 전송한다.
    public String handleMessage(@Payload String message) {
        log.info("{} receives", message);

        return message;
    }
}

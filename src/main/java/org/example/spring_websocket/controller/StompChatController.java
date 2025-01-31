package org.example.spring_websocket.controller;


import java.security.Principal;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.example.spring_websocket.dtos.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class StompChatController {

    /**
     *  "/pub/chat"으로 발행 된 메세지들이 들어옴.(StompConfiguration)
     * */
    @MessageMapping("/chats")
    @SendTo("/sub/chats") // 해당 경로를 구독하는 클라이언트 들에게 메세지를 전송한다.
    public ChatMessage handleMessage(@AuthenticationPrincipal Principal principal, @Payload Map<String, String> payload){

        log.info("{} sent {} ",principal.getName(), payload);

        return new ChatMessage(principal.getName(), payload.get("message"));
    }
}

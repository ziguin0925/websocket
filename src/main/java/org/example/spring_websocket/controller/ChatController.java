package org.example.spring_websocket.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.spring_websocket.entity.ChatRoom;
import org.example.spring_websocket.service.ChatService;
import org.example.spring_websocket.vo.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    /**
     * 채팅방 생성
     * */
    @PostMapping
    public ChatRoom createChatRoom(@AuthenticationPrincipal CustomOAuth2User user, @RequestParam String title) {
        return chatService.createChatRoom(user.getUser(), title);
    }

    /**
     * 채팅방 참여
     * */
    @PostMapping("/{chatroomId}")
    public Boolean joinChatRoom(@AuthenticationPrincipal CustomOAuth2User user, @PathVariable Long chatroomId) {
        return chatService.joinChatRoom(user.getUser(), chatroomId);
    }

    /**
     * 채팅방 나가기
     * */
    @DeleteMapping("/{chatroomId}")
    public Boolean leaveChatRoom(@AuthenticationPrincipal CustomOAuth2User user, @PathVariable Long chatroomId) {
        return chatService.leaveChatRoom(user.getUser(), chatroomId);
    }

    /**
     * 채팅방 목록 조회
     * */
    @GetMapping
    public List<ChatRoom> getChatRoomList(@AuthenticationPrincipal CustomOAuth2User user) {
        return chatService.getChatRoomsList(user.getUser());
    }

}

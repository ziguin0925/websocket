package org.example.spring_websocket.dtos;

import java.time.LocalDateTime;
import org.example.spring_websocket.entity.ChatRoom;

public record ChatRoomDto(
        Long id,
        String title,
        Integer memberCount,
        LocalDateTime createdAt
) {
    public static ChatRoomDto from(ChatRoom chatRoom) {
        return new ChatRoomDto(
                chatRoom.getId(),
                chatRoom.getTitle(),
                chatRoom.getUserChatRoomMappings().size(),
                chatRoom.getCreated());
    }

}

package org.example.spring_websocket.dtos;

public record ChatMessage(
        String sender,
        String message
) {
}

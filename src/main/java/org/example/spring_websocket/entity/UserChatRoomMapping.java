package org.example.spring_websocket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class UserChatRoomMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_chatroom_mapping")
    private Long id;


    @JoinColumn(name="member_id")
    @ManyToOne
    User user;

    @JoinColumn(name="chatroom_id")
    @ManyToOne
    ChatRoom chatRoom;
}

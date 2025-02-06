package org.example.spring_websocket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.spring_websocket.entity.base.BaseTimeEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatRoom extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    @Id
    private Long id;

    String title;

    // UserChatRoomMapping 엔티티가 연관관계의 주인임을 명시.
    @OneToMany(mappedBy = "chatRoom")
    Set<UserChatRoomMapping> userChatRoomMappings;

    public UserChatRoomMapping addUser(User user) {
        if(this.userChatRoomMappings == null) {
            this.userChatRoomMappings = new HashSet<>();
        }
        UserChatRoomMapping userChatRoomMapping = UserChatRoomMapping.builder()
                .user(user)
                .chatRoom(this)
                .build();

        this.userChatRoomMappings.add(userChatRoomMapping);
        return userChatRoomMapping;
    }
}

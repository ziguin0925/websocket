package org.example.spring_websocket.repository;

import java.util.List;
import org.example.spring_websocket.entity.UserChatRoomMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomMappingRepository extends JpaRepository<UserChatRoomMapping, Long> {

    Boolean existsByUserIdAndChatRoomId(Long userId, Long chatRoomId);

    void deleteByUserIdAndChatRoomId(Long userId, Long chatRoomId);

    List<UserChatRoomMapping> findAllByUserId(Long userId);

}

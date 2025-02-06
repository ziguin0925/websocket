package org.example.spring_websocket.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring_websocket.dtos.ChatRoomDto;
import org.example.spring_websocket.entity.ChatRoom;
import org.example.spring_websocket.entity.User;
import org.example.spring_websocket.entity.UserChatRoomMapping;
import org.example.spring_websocket.repository.ChatRoomRepository;
import org.example.spring_websocket.repository.UserChatRoomMappingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomMappingRepository userChatRoomMappingRepository;

    /**
     * 새로운 채팅방 생성.
     * */
    @Transactional
    public ChatRoom createChatRoom(User user, String title){
        ChatRoom chatRoom = ChatRoom.builder()
                .title(title)
                .build();

        chatRoom = chatRoomRepository.save(chatRoom);

        UserChatRoomMapping userChatRoomMapping = chatRoom.addUser(user);

        userChatRoomMapping = userChatRoomMappingRepository.save(userChatRoomMapping);

        return chatRoom;
    }

    /**
     * 채팅방에 참여하기.
     * */
    @Transactional
    public Boolean joinChatRoom(User user, Long chatRoomId){

        if(userChatRoomMappingRepository.existsByUserIdAndChatRoomId(user.getId(),chatRoomId)){
            log.info("이미 참여한 채팅방 입니다.");
            return false;
        }

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();

        UserChatRoomMapping userChatRoomMapping = UserChatRoomMapping.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();

        userChatRoomMapping = userChatRoomMappingRepository.save(userChatRoomMapping);

        return true;
    }

    /**
     * 채팅방에서 나가기.
     * */
    @Transactional
    public Boolean leaveChatRoom(User user, Long chatRoomId){
        if(!userChatRoomMappingRepository.existsByUserIdAndChatRoomId(user.getId(), chatRoomId)){
            log.info("참여하지 않은 방입니다.");
            return false;
        }

        userChatRoomMappingRepository.deleteByUserIdAndChatRoomId(user.getId(), chatRoomId);

        return true;
    }

    /**
     * 해당 유저가 참여한 채팅방 리스트 반환.
     * */
    public List<ChatRoomDto> getChatRoomsList(User user){
        // 맵핑 엔티티를 통해 간접적으로 조회
        List<UserChatRoomMapping> userChatRoomMappingList = userChatRoomMappingRepository.findAllByUserId(user.getId());

        return userChatRoomMappingList.stream()
                .map(UserChatRoomMapping::getChatRoom)
                .map(ChatRoomDto::from)
                .toList();
    }
}

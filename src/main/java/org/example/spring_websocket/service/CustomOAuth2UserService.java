package org.example.spring_websocket.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.spring_websocket.entity.User;
import org.example.spring_websocket.enums.Gender;
import org.example.spring_websocket.repository.UserRepository;
import org.example.spring_websocket.vo.CustomOAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributeMap = oAuth2User.getAttribute("kakao_account");
        String email =  (String) attributeMap.get("email");

        User user = userRepository.findByEmail(email)
                .orElseGet(()-> registerUser(attributeMap));





        return new CustomOAuth2User(user, attributeMap);
    }

    /**
     * Oauth로 처음 가입한 경우.
     * */
    private User registerUser(Map<String, Object> attributeMap) {
        User user = User.builder()
                .email((String) attributeMap.get("email"))
                .nickname((String) ((Map)attributeMap.get("profile")).get("nickname"))
                .name((String) attributeMap.get("name"))
                .phoneNumber((String) attributeMap.get("phone_number"))
                .gender(Gender.valueOf(((String) attributeMap.get("gender")).toUpperCase()))
                .birthDay(getBirthDay(attributeMap))
                .role("USER_ROLE")
                .build();

        return userRepository.save(user);
    }

    private LocalDate getBirthDay(Map<String, Object> attributeMap) {
        String birthYear = (String) attributeMap.get("birthyear");
        String birthday = (String) attributeMap.get("birthday");

        //yyyyMMdd
        return LocalDate.parse(birthYear + birthday, DateTimeFormatter.BASIC_ISO_DATE);
    }
}

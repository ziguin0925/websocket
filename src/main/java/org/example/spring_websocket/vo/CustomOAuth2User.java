package org.example.spring_websocket.vo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.example.spring_websocket.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    User user;
    Map<String, Object> attributes;

    public User getUser(){
        return this.user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()->this.user.getRole());
    }

    @Override
    public String getName() {
        return this.user.getNickname();
    }
}

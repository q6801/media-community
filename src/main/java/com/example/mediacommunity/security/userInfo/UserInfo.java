package com.example.mediacommunity.security.userInfo;

import com.example.mediacommunity.community.domain.member.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@Getter
public class UserInfo extends User implements OAuth2User {
    private Map<String, Object> attributes;

    public UserInfo(Member member) {
        super(member.getLoginId(), member.getPassword(),
                List.of(new SimpleGrantedAuthority(member.getRoleType().getCode())));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return getUsername();
    }

}

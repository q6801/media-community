package com.example.mediacommunity.security.info;

import com.example.mediacommunity.security.info.impl.GoogleOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case "GOOGLE": return new GoogleOAuth2UserInfo(attributes);
            default: throw new RuntimeException("");
        }
    }
}

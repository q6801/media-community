package com.example.mediacommunity.security.userInfo;

import com.example.mediacommunity.security.userInfo.OAuthUserInfoImpl.GoogleOAuth2UserInfo;
import com.example.mediacommunity.security.userInfo.OAuthUserInfoImpl.KakaoOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case "google": return new GoogleOAuth2UserInfo(attributes);
            case "kakao": return new KakaoOAuth2UserInfo(attributes);
            default: throw new RuntimeException("");
        }
    }
}

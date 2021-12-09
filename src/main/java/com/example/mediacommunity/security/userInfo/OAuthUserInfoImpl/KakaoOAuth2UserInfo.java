package com.example.mediacommunity.security.userInfo.OAuthUserInfoImpl;

import com.example.mediacommunity.security.userInfo.OAuth2UserInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        if (properties == null) {
            return null;
        }
        return properties.get("nickname").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("account_email").toString();
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        if (profile == null) {
            return null;
        }
        System.out.println("profile = " + profile);
        return profile.get("thumbnail_image_url").toString();
    }
}

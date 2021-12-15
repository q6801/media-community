package com.example.mediacommunity.security.service;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.member.MemberRepository;
import com.example.mediacommunity.security.userInfo.OAuth2UserInfo;
import com.example.mediacommunity.security.userInfo.OAuth2UserInfoFactory;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();                // google
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()         // sub
                .getUserInfoEndpoint().getUserNameAttributeName();

        System.out.println("userNameAttributeName = " + userNameAttributeName);
        System.out.println("registrationId = " + registrationId);
        System.out.println("oAuth2User = " + oAuth2User);
        System.out.println("userRequest = " + userRequest);

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());
        System.out.println("userInfo = " + userInfo);

        Optional<Member> savedMember = memberRepository.findByLoginId(userInfo.getId());
        Member member;
        if (savedMember.isEmpty()) {
            member = createMember(userInfo, registrationId);
        } else{
            member = savedMember.get();
        }
        return new UserInfo(member);
    }

    private Member createMember(OAuth2UserInfo userInfo, String registrationId) {
        Member member = new Member(userInfo.getId(), "", userInfo.getId(), registrationId, userInfo.getImageUrl());
        return memberRepository.save(member);
    }
}


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
    private final DefaultOAuth2UserService delegate;

//    public CustomOAuth2UserService(MemberRepository memberRepository, DefaultOAuth2UserService defaultOAuth2UserService) {
//        memberRepository = memberRepository;
//        delegate = defaultOAuth2UserService;
//    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String providerType = userRequest.getClientRegistration().getRegistrationId();                // google
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());
        System.out.println("userInfo = " + userInfo);

        Optional<Member> savedMember = memberRepository.findByLoginId(userInfo.getId());
        Member member;
        if (savedMember.isEmpty()) {
            member = Member.createOAuth2Member(userInfo, providerType);
            memberRepository.save(member);
        } else{
            member = savedMember.get();
        }
        return new UserInfo(member);
    }
}


package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.member.MemberRepository;
import com.example.mediacommunity.community.service.Util.BeforeUnitTest;
import com.example.mediacommunity.security.BadProviderException;
import com.example.mediacommunity.security.service.CustomOAuth2UserService;
import com.example.mediacommunity.security.service.CustomUserDetailsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.DelegatingOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserSecurityTest {
    @Mock
    private MemberRepository memberRepository;
//    @Mock
//    private DefaultOAuth2UserService defaultOAuth2UserService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;
    @InjectMocks
    private CustomOAuth2UserService customOAuth2UserService;

    private BeforeUnitTest beforeUnitTest;

    @BeforeEach
    public void beforeTest() {
        beforeUnitTest = new BeforeUnitTest();
    }
    
    @Test
    @DisplayName("user id로 userDetail 조회 성공")
    public void successToLoadUserByUsername() {
        Member member = beforeUnitTest.getMembers().get(0);
        member.setProvider("local");
        given(memberRepository.findByLoginId(member.getLoginId()))
                .willReturn(Optional.of(member));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(member.getLoginId());
        assertThat(userDetails.getUsername())
                .isEqualTo(member.getLoginId());
        assertThat(userDetails.getPassword())
                .isEqualTo(member.getPassword());
    }

    @Test
    @DisplayName("user id로 local userDetail 조회 성공")
    public void failToLoadUserByUsername() {
        Member member = beforeUnitTest.getMembers().get(0);
        member.setProvider("local");
        given(memberRepository.findByLoginId(member.getLoginId()))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(
                () -> customUserDetailsService.loadUserByUsername(member.getLoginId())
        ).isInstanceOf(InternalAuthenticationServiceException.class);
    }

    @Test
    @DisplayName("user id로 local userDetail 조회 성공")
    public void failToLoadUserByWrongProvider() {
        Member member = beforeUnitTest.getMembers().get(0);
        member.setProvider("google");
        given(memberRepository.findByLoginId(member.getLoginId()))
                .willReturn(Optional.of(member));

        Assertions.assertThatThrownBy(
                () -> customUserDetailsService.loadUserByUsername(member.getLoginId())
        ).isInstanceOf(BadProviderException.class);
    }

    @Test
    @DisplayName("oauth userDetail 저장 및 조회 성공")
    public void successToLoadUserByOAuthWithSaving() {
        OAuth2UserRequest oAuth2UserRequest = mock(OAuth2UserRequest.class);
        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId("google")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientId("client id")
                .tokenUri("token")
                .build();
        given(oAuth2UserRequest.getClientRegistration())
                .willReturn(clientRegistration);

        DefaultOAuth2UserService defaultOAuth2UserService = mock(DefaultOAuth2UserService.class);
        OAuth2User oAuth2User = mock(OAuth2User.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sub", "sub");
        map.put("name", "name");
        map.put("email", "email");
        map.put("picture", "picture");
        given(oAuth2User.getAttributes()).willReturn(map);
        given(defaultOAuth2UserService.loadUser(any(OAuth2UserRequest.class))).willReturn(oAuth2User);

        Member member = beforeUnitTest.getMembers().get(0);
        given(memberRepository.findByLoginId(any(String.class)))
                .willReturn(Optional.of(member));

        CustomOAuth2UserService customOAuth2UserService = new CustomOAuth2UserService(memberRepository, defaultOAuth2UserService);
        customOAuth2UserService.loadUser(oAuth2UserRequest);
    }
}

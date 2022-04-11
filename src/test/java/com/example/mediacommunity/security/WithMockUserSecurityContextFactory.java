package com.example.mediacommunity.security;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.security.userInfo.UserInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserInfo userInfo = new UserInfo(Member.builder()
                .loginId(annotation.id())
                .nickname(annotation.name())
                .password("password").build());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userInfo, "password",
                        AuthorityUtils.createAuthorityList(annotation.role()));
        context.setAuthentication(authentication);
        return context;

    }
}

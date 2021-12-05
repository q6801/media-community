package com.example.mediacommunity.security.handler;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberRepository;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * 현재 폐기된 설정
 */
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("authentication = " + authentication);
        Member member = ((UserInfo) authentication.getPrincipal()).getMember();
        if (memberRepository.findByLoginId(member.getLoginId()).isEmpty()) {
            request.getRequestDispatcher("/fuck").forward(request, response);
        } else {
            request.getRequestDispatcher("/").forward(request, response);
        }
    }
}

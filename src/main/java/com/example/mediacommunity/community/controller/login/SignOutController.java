package com.example.mediacommunity.community.controller.login;

import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SignOutController {
    private final MemberService memberService;

    @PostMapping("/signout")
    public String signout(@AuthenticationPrincipal UserInfo userInfo, HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        memberService.signOut(userInfo.getUsername());
        return "redirect:/";
    }
}

package com.example.mediacommunity.community.controller.login;

import com.example.mediacommunity.common.annotation.AuthUser;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SignOutController {
    private final MemberService memberService;

    @PostMapping("/signout")
    public String signout(@AuthUser Member member, HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        memberService.signOut(member);
        return "redirect:/";
    }
}

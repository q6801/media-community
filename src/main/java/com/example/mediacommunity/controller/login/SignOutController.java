package com.example.mediacommunity.controller.login;

import com.example.mediacommunity.constant.SessionConst;
import com.example.mediacommunity.domain.member.Member;
import com.example.mediacommunity.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SignOutController {
    private final MemberService memberService;

    @PostMapping("/signout")
    public String signout(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                          HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        memberService.signOut(member);
        return "redirect:/";
    }
}

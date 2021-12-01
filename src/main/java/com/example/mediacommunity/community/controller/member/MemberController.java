package com.example.mediacommunity.community.controller.member;

import com.example.mediacommunity.common.annotation.AuthUser;
import com.example.mediacommunity.community.domain.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
    @GetMapping()
    public String memberInfo(@AuthUser Member member, Model model) {
        model.addAttribute("member", member);
        return "member/memberInfo";
    }
}

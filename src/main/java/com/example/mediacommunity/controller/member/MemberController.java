package com.example.mediacommunity.controller.member;

import com.example.mediacommunity.annotation.AuthUser;
import com.example.mediacommunity.constant.SessionConst;
import com.example.mediacommunity.domain.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/member")
public class MemberController {
    @GetMapping()
    public String memberInfo(@AuthUser Member member, Model model) {
        model.addAttribute("member", member);
        return "member/memberInfo";
    }
}

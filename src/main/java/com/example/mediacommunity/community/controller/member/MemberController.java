package com.example.mediacommunity.community.controller.member;

import com.example.mediacommunity.common.annotation.AuthUser;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberEditDto;
import com.example.mediacommunity.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping()
    public String memberInfo(@AuthUser Member member, Model model) {
        model.addAttribute("member", member);
        return "member/memberInfo";
    }

    @GetMapping("/edit")
    public String editMemberForm(@AuthUser Member member, Model model) {
        model.addAttribute("imageUrl", member.getImageUrl());
        model.addAttribute("memberEditDto", new MemberEditDto(null, member.getNickname()));
        model.addAttribute("role", member.getRoleType().getCode());
        return "member/memberEdit";
    }

    @PostMapping("/edit")
    public String editMemberInfo(@AuthUser Member member, @Valid @ModelAttribute MemberEditDto memberEditDto,
                                 BindingResult bindingResult, HttpSession session, Model model) throws ServletException, IOException {

        if (bindingResult.hasErrors()) {
            return "member/memberEdit";
        }
        Optional<String> imageUrl = memberService.updateProfile(member.getLoginId(), memberEditDto);
        if (imageUrl.isEmpty()) {
            bindingResult.reject("nicknameDuplicated");
            return "member/memberEdit";
        } else {
            model.addAttribute("imageUrl", imageUrl.get());
        }
        session.invalidate();
        return "redirect:/";
    }


}

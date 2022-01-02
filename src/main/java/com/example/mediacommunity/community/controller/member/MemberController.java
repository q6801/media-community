package com.example.mediacommunity.community.controller.member;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberEditDto;
import com.example.mediacommunity.community.domain.member.RoleType;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String memberInfo(@AuthenticationPrincipal UserInfo userInfo, Model model) {
        Member authUser = memberService.findMemberById(userInfo.getUsername());
        model.addAttribute("member", authUser);
        return "member/memberInfo";
    }

    @GetMapping("/edit")
    public String editMemberForm(@AuthenticationPrincipal UserInfo userInfo, Model model) {
        Member authUser = memberService.findMemberById(userInfo.getUsername());
        model.addAttribute("imageUrl", authUser.getImageUrl());
        model.addAttribute("memberEditDto", new MemberEditDto(null, authUser.getNickname()));
        model.addAttribute("role", authUser.getRoleType().getCode());
        return "member/memberEdit";
    }

    @PostMapping("/edit")
    public String editMemberInfo(@AuthenticationPrincipal UserInfo userInfo, @Valid @ModelAttribute MemberEditDto memberEditDto,
                                 BindingResult bindingResult, HttpSession session, Model model) throws ServletException, IOException {

        String role = userInfo.getRole();
        System.out.println("role = " + role);
        model.addAttribute("role", role);
        if (bindingResult.hasErrors()) {
            return "member/memberEdit";
        }
        Optional<String> imageUrl = memberService.updateProfile(userInfo.getUsername(), memberEditDto);
        if (imageUrl.isEmpty()) {
            bindingResult.reject("nicknameDuplicated");
            return "member/memberEdit";
        } else {
            model.addAttribute("imageUrl", imageUrl.get());
        }
        return "redirect:/";
    }
}

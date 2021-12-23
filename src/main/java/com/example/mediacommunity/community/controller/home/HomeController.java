package com.example.mediacommunity.community.controller.home;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserInfo userInfo, Model model, HttpSession httpSession) {
        httpSession.getAttributeNames().asIterator()
                .forEachRemaining((name) -> log.info("{} : {}", name, httpSession.getAttribute(name)));
        if (userInfo == null) {
            return "home/home";
        }
        Member authUser = memberService.findMemberById(userInfo.getUsername());
        model.addAttribute("nickname", authUser.getNickname());
        return "home/loginHome";
    }
}

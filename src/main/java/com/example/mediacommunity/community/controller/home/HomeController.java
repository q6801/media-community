package com.example.mediacommunity.community.controller.home;

import com.example.mediacommunity.common.annotation.AuthUser;
import com.example.mediacommunity.community.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String home(@AuthUser Member member, Model model, HttpSession httpSession) {
        httpSession.getAttributeNames().asIterator()
                .forEachRemaining((name) -> log.info("{} : {}", name, httpSession.getAttribute(name)));

        if (member == null) {
            return "home/home";
        }
        model.addAttribute("nickname", member.getNickname());
        return "home/loginHome";
    }
}

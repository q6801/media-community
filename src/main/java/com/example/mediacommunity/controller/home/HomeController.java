package com.example.mediacommunity.controller.home;

import com.example.mediacommunity.annotation.AuthUser;
import com.example.mediacommunity.constant.SessionConst;
import com.example.mediacommunity.domain.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@AuthUser Member member, Model model) {
        if (member == null) {
            return "home/home";
        }
        model.addAttribute("nickname", member.getNickname());
        return "home/loginHome";
    }
}

package com.example.mediacommunity.controller.login;

import com.example.mediacommunity.domain.member.LoginDto;
import com.example.mediacommunity.domain.member.Member;
import com.example.mediacommunity.domain.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    @Autowired
    MemberRepository memberRepository;

    @GetMapping
    public String signUpForm() {
        return "login/signUpForm";
    }

    @PostMapping
    public String signUp(@ModelAttribute LoginDto loginDto) {
        memberRepository.save(new Member(loginDto.getLoginId(),
                loginDto.getPassword(), loginDto.getNickname()));
        return "redirect:/";
    }
}

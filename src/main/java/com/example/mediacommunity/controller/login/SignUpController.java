package com.example.mediacommunity.controller.login;

import com.example.mediacommunity.domain.member.LoginDto;
import com.example.mediacommunity.domain.member.Member;
import com.example.mediacommunity.domain.member.MemberRepository;
import com.example.mediacommunity.domain.member.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    @Autowired
    MemberRepository memberRepository;

    @GetMapping
    public String signUpForm(Model model) {
        model.addAttribute("member", new SignUpDto());
        return "login/signUpForm";
    }

    @PostMapping
    public String signUp(@Valid @ModelAttribute("member") SignUpDto signUpDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/signUpForm";
        }
        memberRepository.save(new Member(signUpDto.getLoginId(),
                signUpDto.getPassword(), signUpDto.getNickname()));
        return "redirect:/";
    }
}

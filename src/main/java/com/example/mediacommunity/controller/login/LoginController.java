package com.example.mediacommunity.controller.login;

import com.example.mediacommunity.constant.SessionConst;
import com.example.mediacommunity.domain.member.LoginDto;
import com.example.mediacommunity.domain.member.Member;
import com.example.mediacommunity.domain.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public String loginForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                            Member member) {
        return "home/loginForm";
    }

    @PostMapping
    public String login(@ModelAttribute("loginDto") LoginDto loginDto,
                        HttpServletRequest request) {
        String loginId = loginDto.getLoginId();
        Member member = memberRepository.findByLoginId(loginId);
        if(!passwordEquals(loginDto, member)) {
            return "/login/loginForm";
        }
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return "redirect:/";
    }

    private boolean passwordEquals(LoginDto loginDto, Member member) {
        return member.getPassword().equals(loginDto.getPassword());
    }
}

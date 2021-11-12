package com.example.mediacommunity.controller.login;

import com.example.mediacommunity.constant.SessionConst;
import com.example.mediacommunity.domain.member.LoginDto;
import com.example.mediacommunity.domain.member.Member;
import com.example.mediacommunity.domain.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("member", new LoginDto());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("member") LoginDto loginDto,
                        BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "/login/loginForm";
        }

        String loginId = loginDto.getLoginId();
        Member member = memberRepository.findByLoginId(loginId);
        if (!passwordEquals(loginDto, member)) {
            log.info("password error");
            return "/login/loginForm";
        }

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return "redirect:/";
    }

    private boolean passwordEquals(LoginDto loginDto, Member member) {
        return member.getPassword().equals(loginDto.getPassword());
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}

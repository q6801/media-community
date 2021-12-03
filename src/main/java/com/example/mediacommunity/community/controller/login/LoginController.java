package com.example.mediacommunity.community.controller.login;

import com.example.mediacommunity.community.domain.member.LoginDto;
import com.example.mediacommunity.community.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("member", new LoginDto());
        return "login/loginForm";
    }

//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute("member") LoginDto loginDto,
//                        BindingResult bindingResult, HttpServletRequest request,
//                        @RequestParam(defaultValue = "/") String redirectURL) {
//
//        Member member = memberService.login(loginDto, bindingResult);
//        if (bindingResult.hasErrors()) {
//            log.info("error={}", bindingResult);
//            return "/login/loginForm";
//        }
//        HttpSession httpSession = request.getSession();
//        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, member);
//        return "redirect:" + redirectURL;
//    }

    @PostMapping("/loginFail")
    public String loginFail(@Valid @ModelAttribute("member") LoginDto loginDto,
                            BindingResult bindingResult, HttpServletRequest request,
                            @RequestParam(defaultValue = "/") String redirectURL, RedirectAttributes redirectAttributes) {
        System.out.println("login fail");


        if ((Boolean) request.getAttribute("idFail")) bindingResult.reject("idFail");
        if ((Boolean) request.getAttribute("pwFail")) bindingResult.reject("pwFail");

        log.info("error={}", bindingResult);
        redirectAttributes.addAttribute(redirectURL);
        return "/login/loginForm";
    }


}

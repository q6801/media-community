package com.example.mediacommunity.community.controller.login;

import com.example.mediacommunity.community.domain.member.SignUpDto;
import com.example.mediacommunity.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {
    private final CustomUserDetailsService userService;

    @GetMapping
    public String signUpForm(Model model) {
        model.addAttribute("member", new SignUpDto());
        return "login/signUpForm";
    }

    @PostMapping
    public String signUp(@Valid @ModelAttribute("member") SignUpDto signUpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/signUpForm";
        }
        if (!userService.saveForSignUp(signUpDto)) {
            bindingResult.reject("signUpFail");
            return "login/signUpForm";
        }
        return "redirect:/";
    }
}

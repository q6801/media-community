package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.community.service.EmailConfirmationTokenService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final EmailConfirmationTokenService emailTokenService;
    private final MemberService memberService;

    @PostMapping("/wait-email")
    public String sendEmailToken(@AuthenticationPrincipal UserInfo userInfo, @RequestParam String email) {
        System.out.println("email = " + email);
        emailTokenService.saveAndSendEmailToken(userInfo.getUsername(), email);
        return "redirect:/wait-email";
    }

    @GetMapping("/wait-email")
    public String waitEmail() {
        return "email/waitEmail";
    }

    @GetMapping("/confirm-email")
    public String confirmEmailToken(@AuthenticationPrincipal UserInfo userInfo, @RequestParam UUID token) {
        if (emailTokenService.confirmEmail(token)) {
            memberService.updateMemberRoleToUser(userInfo.getUsername());
            return "redirect:/member";
        }
        return "redirect:/member";
    }
}

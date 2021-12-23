package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.common.annotation.AuthUser;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.service.EmailConfirmationTokenService;
import com.example.mediacommunity.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final EmailConfirmationTokenService emailTokenService;
    private final MemberService memberService;

    @PostMapping("/wait-email")
    public String sendEmailToken(@AuthUser Member member, @RequestParam String email) {
        System.out.println("email = " + email);
        emailTokenService.saveAndSendEmailToken(member.getLoginId(), email);
        return "redirect:/wait-email";
    }

    @GetMapping("/wait-email")
    public String waitEmail() {
        return "email/waitEmail";
    }

    @GetMapping("/confirm-email")
    public String confirmEmailToken(@AuthUser Member member, @RequestParam UUID token) {
        if (emailTokenService.confirmEmail(token)) {
            memberService.updateMemberRoleToUser(member.getLoginId());
            return "redirect:/member";
        }
        return "redirect:/member";
    }
}

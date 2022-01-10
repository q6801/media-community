package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.community.service.EmailConfirmationTokenService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailConfirmationTokenService emailTokenService;
    private final MemberService memberService;

    @PostMapping("/wait-email")
    public ResponseEntity<?> sendEmailToken(@AuthenticationPrincipal UserInfo userInfo, @RequestBody Map<String, String> email) {
        System.out.println("email = " + email);
        emailTokenService.saveAndSendEmailToken(userInfo.getUsername(), email.get("email"));
        return ResponseEntity.status(HttpStatus.CREATED).build();
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

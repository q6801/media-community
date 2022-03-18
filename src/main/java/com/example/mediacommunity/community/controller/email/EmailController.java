package com.example.mediacommunity.community.controller.email;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.BadValueException;
import com.example.mediacommunity.community.service.email.EmailConfirmationTokenService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailConfirmationTokenService emailTokenService;
    private final MemberService memberService;

    @PostMapping("/email")
    public ResponseEntity<?> sendEmailToken(@AuthenticationPrincipal UserInfo userInfo, @RequestBody Map<String, String> email) {
        System.out.println("email = " + email);
        emailTokenService.saveAndSendEmailToken(userInfo.getUsername(), email.get("email"));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/confirm-email")
    public  ResponseEntity<?> confirmEmailToken(@AuthenticationPrincipal UserInfo userInfo, @RequestBody Map<String, Integer> verifyingNum) {
        if (emailTokenService.confirmEmail(userInfo.getUsername(), verifyingNum.get("verifyingNum"))) {
            memberService.updateMemberRoleToUser(userInfo.getUsername());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        throw new BadValueException(ExceptionEnum.BAD_VALUE);
    }
}

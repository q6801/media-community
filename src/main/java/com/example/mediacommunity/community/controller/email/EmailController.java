package com.example.mediacommunity.community.controller.email;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.BadValueException;
import com.example.mediacommunity.community.service.email.EmailConfirmationTokenService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import com.example.mediacommunity.utils.ApiResult;
import com.example.mediacommunity.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class EmailController {

    private final EmailConfirmationTokenService emailTokenService;
    private final MemberService memberService;

    @PostMapping("/email")
    public ApiResult<?> sendEmailToken(@AuthenticationPrincipal UserInfo userInfo, @RequestBody Map<String, String> email) {
        System.out.println("email = " + email);
        emailTokenService.saveAndSendEmailToken(userInfo.getUsername(), email.get("email"));
        return ApiUtils.success(null);
    }

    @PostMapping("/confirm-email")
    public ApiResult<Object> confirmEmailToken(@AuthenticationPrincipal UserInfo userInfo, @RequestBody Map<String, Integer> verifyingNum) {
        if (emailTokenService.confirmEmail(userInfo.getUsername(), verifyingNum.get("verifyingNum"))) {
            memberService.updateMemberRoleToUser(userInfo.getUsername());
            return ApiUtils.success(null);
        }
        throw new BadValueException(ExceptionEnum.BAD_VALUE);
    }
}

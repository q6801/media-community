package com.example.mediacommunity.community.controller.member;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.UserInfoNotFoundException;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberEditDto;
import com.example.mediacommunity.community.domain.member.MemberDto;
import com.example.mediacommunity.community.domain.member.SignUpDto;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import com.example.mediacommunity.utils.ApiResult;
import com.example.mediacommunity.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member")
    public ApiResult<MemberDto> member(@AuthenticationPrincipal UserInfo userInfo) {
        if (userInfo == null) {
            throw new UserInfoNotFoundException(ExceptionEnum.USER_INFO_NOT_FOUND);
        }
        Member member = memberService.findMemberById(userInfo.getUsername());
        MemberDto memberDto = new MemberDto(member.getLoginId(), member.getEmail(),
                member.getNickname(), member.getImageUrl());
        return ApiUtils.success(memberDto);
    }

    @PutMapping("/member")
    public ApiResult<?> editMember(@ModelAttribute MemberEditDto memberEditDto,
                                        @AuthenticationPrincipal UserInfo userInfo) throws IOException {
        String role = userInfo.getRole();
        System.out.println("role = " + role);

        Optional<String> imageUrl = memberService.updateProfile(userInfo.getUsername(), memberEditDto);
        imageUrl.orElseThrow(() -> new RuntimeException());

        return ApiUtils.success(null);
    }

    @PostMapping("/signup")
    public ApiResult<Object> signUp(@RequestBody SignUpDto signUpDto) {
        memberService.encodeAndSave(signUpDto);
        return ApiUtils.success(null);
    }

    @DeleteMapping("/member")
    public ApiResult<Object> signout(@AuthenticationPrincipal UserInfo userInfo, HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        memberService.signOut(userInfo.getUsername());
        return ApiUtils.success(null);
    }


}

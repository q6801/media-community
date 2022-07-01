package com.example.mediacommunity.community.controller.member;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.UserInfoNotFoundException;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberDto;
import com.example.mediacommunity.community.domain.member.MemberEditDto;
import com.example.mediacommunity.community.domain.member.SignUpDto;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import com.example.mediacommunity.utils.ApiResult;
import com.example.mediacommunity.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        MemberDto memberDto = memberService.findMemberById(userInfo.getUsername()).convertMemberToDto();
        return ApiUtils.success(memberDto);
    }

    @PutMapping("/member")
    public ApiResult<?> editMember(@Valid @ModelAttribute MemberEditDto memberEditDto,
                                        @AuthenticationPrincipal UserInfo userInfo) throws IOException {
        Optional<String> imageUrl = memberService.updateProfile(userInfo.getUsername(), memberEditDto);
        imageUrl.orElseThrow(RuntimeException::new);

        return ApiUtils.success(null);
    }

    @PostMapping("/signup")
    public ApiResult<Object> signUp(@Valid @RequestBody SignUpDto signUpDto, HttpServletResponse response) throws IOException {
        memberService.encodeAndSave(signUpDto);
        response.sendRedirect("/");
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

package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.email.EmailConfirmationToken;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.EmailConfirmationTokenRepository;
import com.example.mediacommunity.community.repository.member.MemberRepository;
import com.example.mediacommunity.community.service.email.EmailConfirmationTokenService;
import com.example.mediacommunity.community.service.email.EmailSenderService;
import com.example.mediacommunity.community.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmailConfirmationTokenServiceTest {
    @Mock
    private EmailConfirmationTokenRepository emailRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private EmailConfirmationTokenService emailService;


    @Test
    public void delete() {
        emailService.delete(getStubEmailTokenList().get(0));
    }

    @Test
    public void successToSendAndConfirm() {
        Member member = getStubMemberList().get(0);
        given(memberService.findMemberById(member.getLoginId()))
                .willReturn(member);

        emailService.saveAndSendEmailToken(member.getLoginId(), "hi@example.com");
    }

    @Test
    @DisplayName("이메일 token confirm 성공")
    public void successToConfirmEmail() {
        Member member = getStubMemberList().get(0);
        EmailConfirmationToken emailToken = EmailConfirmationToken.createEmailToken(member, "hello@example.com");
        member.setEmailTokens(Arrays.asList(emailToken));

        given(memberService.findMemberById(member.getLoginId()))
                .willReturn(member);
        given(emailRepository.findRecentMail(member))
                .willReturn(Optional.of(member.getEmailTokens().get(0)));

        boolean result = emailService.confirmEmail(member.getLoginId(), emailToken.getRandomNum());
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이메일 token confirm 성공")
    public void failToConfirmEmailWithExpired() {
        Member member = getStubMemberList().get(0);
        EmailConfirmationToken emailToken = EmailConfirmationToken.createEmailToken(member, "hello@example.com");
        emailToken.useToken();
        given(memberService.findMemberById(member.getLoginId()))
                .willReturn(member);
        given(emailRepository.findRecentMail(member))
                .willReturn(Optional.of(emailToken));

        boolean result = emailService.confirmEmail(member.getLoginId(), emailToken.getRandomNum());
        Assertions.assertThat(result).isFalse();
    }

//    public boolean confirmEmail(String memberId, int verifyingNum) {
//        Member member = memberService.findMemberById(memberId);
//        EmailConfirmationToken emailToken = emailTokenRepository.findRecentMail(member).orElseThrow();
//
//        if (notUsedOrExpired(emailToken) &&
//                emailToken.getRandomNum() == verifyingNum) {
//            member.setEmail(emailToken.getEmailAddress());
//            memberService.updateMemberRoleToUser(memberId);
//            emailToken.useToken();
//            return true;
//        }
//        return false;
//    }


    private List<Member> getStubMemberList() {
        return Arrays.asList(
                Member.builder()
                        .loginId("test121")
                        .nickname("test1!")
                        .password("password0").build(),
                Member.builder()
                        .loginId("test1232")
                        .nickname("test!")
                        .password("password1").build()
        );
    }

    private List<EmailConfirmationToken> getStubEmailTokenList() {
        return Arrays.asList(
                EmailConfirmationToken.createEmailToken(
                                getStubMemberList().get(0), "email0.com"),
                EmailConfirmationToken.createEmailToken(
                        getStubMemberList().get(1), "email1.com")
        );
    }
}
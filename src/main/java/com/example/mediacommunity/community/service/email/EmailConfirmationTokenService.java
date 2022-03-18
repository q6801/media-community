package com.example.mediacommunity.community.service.email;

import com.example.mediacommunity.community.domain.email.EmailConfirmationToken;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.EmailConfirmationTokenRepository;
import com.example.mediacommunity.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Transactional
public class EmailConfirmationTokenService {
    private final EmailConfirmationTokenRepository emailTokenRepository;
    private final EmailSenderService emailSenderService;
    private final MemberService memberService;


//    public EmailConfirmationToken findById(UUID id) {
//        return emailTokenRepository.findRecentMail(id);
//    }

    public void save(EmailConfirmationToken emailToken) {
        emailTokenRepository.save(emailToken);
    }

    public void delete(EmailConfirmationToken emailToken) {
        emailTokenRepository.delete(emailToken);
    }

    @Async
    public void saveAndSendEmailToken(String memberId, String emailAddress) {
        Member member = memberService.findMemberById(memberId);

        EmailConfirmationToken emailToken = EmailConfirmationToken.createEmailToken(member, emailAddress);
        emailTokenRepository.save(emailToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("[media community] 회원 가입 이메일 인증");
        mailMessage.setText("입력 번호 : " + emailToken.getRandomNum() + " ");
        emailSenderService.sendMail(mailMessage);
    }

    public boolean confirmEmail(String memberId, int verifyingNum) {
        Member member = memberService.findMemberById(memberId);
        EmailConfirmationToken emailToken = emailTokenRepository.findRecentMail(member).orElseThrow();

        if (notUsedOrExpired(emailToken) &&
                emailToken.getRandomNum() == verifyingNum) {
            member.setEmail(emailToken.getEmailAddress());
            memberService.updateMemberRoleToUser(memberId);
            emailToken.useToken();
            return true;
        }
        return false;
    }

    private boolean notUsedOrExpired(EmailConfirmationToken emailToken) {
        boolean expired = emailToken.getExpiredAt().before(Timestamp.valueOf(LocalDateTime.now()));

        if (!(emailToken.isUsed() || expired)) {
            return true;
        }
        return false;
    }
}

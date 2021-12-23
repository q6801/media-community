package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.EmailConfirmationToken;
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
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class EmailConfirmationTokenService {
    private final EmailConfirmationTokenRepository emailTokenRepository;
    private final EmailSenderService emailSenderService;
    private final MemberService memberService;


    public EmailConfirmationToken findById(UUID id) {
        return emailTokenRepository.findById(id);
    }

    public void save(EmailConfirmationToken emailToken) {
        emailTokenRepository.save(emailToken);
    }

    public void delete(EmailConfirmationToken emailToken) {
        emailTokenRepository.delete(emailToken);
    }

    @Async
    public void saveAndSendEmailToken(String memberId, String emailAddress) {
        Member member = memberService.findMemberById(memberId);

        EmailConfirmationToken emailToken = EmailConfirmationToken.createEmailToken(member);
        emailTokenRepository.save(emailToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("[media community] 회원 가입 이메일 인증");
        mailMessage.setText("http://localhost:8080/confirm-email?token=" + emailToken.getId());
        emailSenderService.sendMail(mailMessage);

        member.setEmail(emailAddress);  // 관리가 되나?
    }

    public boolean confirmEmail(UUID token) {
        EmailConfirmationToken emailToken = emailTokenRepository.findById(token);
        boolean expired = emailToken.getExpiredAt().before(Timestamp.valueOf(LocalDateTime.now()));
        if (emailToken.isExpired() || expired) {
            return false;
        }
        emailToken.useToken();
        return true;
    }
}

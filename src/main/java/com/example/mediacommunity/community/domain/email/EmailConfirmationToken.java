package com.example.mediacommunity.community.domain.email;

import com.example.mediacommunity.community.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailConfirmationToken {

    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5l;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private boolean used;
    private int randomNum;

    @CreationTimestamp
    private Timestamp createdAt;

    private Timestamp expiredAt;

    private String emailAddress;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    public static EmailConfirmationToken createEmailToken(Member member, String emailAddress) {
        EmailConfirmationToken emailToken = new EmailConfirmationToken();
        emailToken.expiredAt= Timestamp.valueOf(
                LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE).withNano(0));
        emailToken.member = member;
        emailToken.used = false;
        emailToken.randomNum = ThreadLocalRandom.current().nextInt(100000, 999999);
        emailToken.emailAddress = emailAddress;
        return emailToken;
    }
    public void useToken() {
        this.used = true;
    }
}

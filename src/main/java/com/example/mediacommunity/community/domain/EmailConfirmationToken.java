package com.example.mediacommunity.community.domain;

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

    private boolean expired;

    @CreationTimestamp
    private Timestamp createdAt;

    private Timestamp expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    public static EmailConfirmationToken createEmailToken(Member member) {
        EmailConfirmationToken emailToken = new EmailConfirmationToken();
        emailToken.expiredAt= Timestamp.valueOf(
                LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE).withNano(0));
        emailToken.member = member;
        emailToken.expired = false;
        return emailToken;
    }
    public void useToken() {
        this.expired = true;
    }
}

package com.example.mediacommunity.community.domain.member;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.email.EmailConfirmationToken;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.security.userInfo.OAuth2UserInfo;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude = {"boards", "replies", "hearts", "emailTokens"})
@Table(name="Members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    private String loginId;
    private String email;
    private String password;
    private String nickname;
    private String provider;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.GUEST;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy= "member", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<EmailConfirmationToken> emailTokens = new ArrayList<>();

    @Builder
    private Member(String loginId, String password, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    };

    public static Member createLocalMember(SignUpDto signUpDto, PasswordEncoder encoder, String imageUrl) {
        Member member = Member.builder()
                .loginId(signUpDto.getLoginId())
                .password(encoder.encode(signUpDto.getPassword()))
                .nickname(signUpDto.getNickname())
                .build();

        member.setImageUrl(imageUrl);
        member.provider = "local";
        member.roleType = RoleType.GUEST;
        return member;
    }

    public static Member createOAuth2Member(OAuth2UserInfo userInfo, String providerName) {
        Member member = Member.builder()
                .loginId(userInfo.getId())
                .password("")
                .nickname(userInfo.getId()).build();

        member.imageUrl = userInfo.getImageUrl();
        member.email = userInfo.getEmail();
        member.provider = providerName;
        member.roleType = RoleType.USER;
        return member;
    }
}

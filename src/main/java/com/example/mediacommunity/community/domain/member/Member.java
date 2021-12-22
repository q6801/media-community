package com.example.mediacommunity.community.domain.member;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.security.userInfo.OAuth2UserInfo;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
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

    @OneToMany(mappedBy="member", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Heart> hearts = new ArrayList<>();

    @Builder
    private Member(String loginId, String password, String nickname, String imageUrl) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    };

    public void hidePassword() {
        this.password="";
    }

    public static Member createLocalMember(SignUpDto signUpDto, PasswordEncoder encoder, String defaultImageUrl) {
        Member member = Member.builder()
                .loginId(signUpDto.getLoginId())
                .password(encoder.encode(signUpDto.getPassword()))
                .nickname(signUpDto.getNickname())
                .imageUrl(defaultImageUrl).build();

        member.provider = "local";
        member.roleType = RoleType.GUEST;
        return member;
    }

    public static Member createOAuth2Member(OAuth2UserInfo userInfo, String providerName) {
        Member member = Member.builder()
                .loginId(userInfo.getId())
                .password("")
                .nickname(userInfo.getId())
                .imageUrl(userInfo.getImageUrl()).build();
        member.email = userInfo.getEmail();
        member.provider = providerName;
        member.roleType = RoleType.USER;
        return member;
    }
}

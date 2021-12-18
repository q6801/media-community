package com.example.mediacommunity.community.domain.member;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.reply.Reply;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="Members")
@NoArgsConstructor
public class Member {
    @Id
    private String loginId;
    private String password;
    private String nickname;
    private String provider;
    private String imageUrl;

    @OneToMany(mappedBy="member", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy="member", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Heart> hearts = new ArrayList<>();

    public Member(String loginId, String password, String nickname, String provider, String imageUrl) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.provider = provider;
        this.imageUrl = imageUrl;
    };
}

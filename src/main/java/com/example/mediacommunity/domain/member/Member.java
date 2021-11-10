package com.example.mediacommunity.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
@AllArgsConstructor
@Data
public class Member {
    private Long id;
    private String loginId;
    private String password;
    private String nickname;
    public Member(String longinId, String password, String nickname) {
        this.loginId = longinId;
        this.password = password;
        this.nickname = nickname;
    }
}

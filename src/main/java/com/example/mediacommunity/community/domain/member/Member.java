package com.example.mediacommunity.community.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Member {
    private String loginId;
    private String password;
    private String nickname;
    private String provider;

    public Member() {};
}

package com.example.mediacommunity.community.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberInfoDto {
    private String loginId;
    private String email;
    private String nickname;
    private String imageUrl;
}

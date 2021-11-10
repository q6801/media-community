package com.example.mediacommunity.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
    private String loginId;
    private String password;
    private String nickname;
}

package com.example.mediacommunity.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginDto {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    public LoginDto() {}
}

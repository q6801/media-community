package com.example.mediacommunity.community.domain.member;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    @NotBlank(message = "id는 빈 값이 될 수 없습니다.")
    @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "ID는 영문, 숫자로 이뤄진 6~12글자여야 합니다.")
    private String loginId;

    @NotBlank(message = "password는 빈 값이 될 수 없습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "비밀번호는 최소 8자이며, 하나 이상의 문자와 숫자로 이뤄져야합니다.")
    private String password;

    @NotBlank(message = "password 재확인은 빈 값이 될 수 없습니다.")
    private String passwordChecker;

    @NotBlank(message = "nickname은 빈 값이 될 수 없습니다.")
    private String nickname;
}

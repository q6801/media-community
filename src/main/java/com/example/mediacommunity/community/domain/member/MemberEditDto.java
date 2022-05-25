package com.example.mediacommunity.community.domain.member;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberEditDto {
    private MultipartFile file;

    @NotBlank(message = "nickname은 빈 값이 될 수 없습니다.")
    private String nickname;
}

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

    @NotBlank
    private String nickname;
}

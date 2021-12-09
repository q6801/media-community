package com.example.mediacommunity.community.domain.member;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class MemberEditDto {
    private MultipartFile file;

    @NotBlank
    private String nickname;
}

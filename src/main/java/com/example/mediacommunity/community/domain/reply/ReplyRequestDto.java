package com.example.mediacommunity.community.domain.reply;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequestDto {
    @NotBlank(message = "내용은 빈 값이 될 수 없습니다.")
    private String content;
}

package com.example.mediacommunity.community.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {
    @NotBlank(message = "title은 빈 값이 될 수 없습니다.")
    private String title;

    @NotBlank(message = "본문은 빈 값이 될 수 없습니다.")
    private String content;

    @NotBlank(message = "category는 빈 값이 될 수 없습니다.")
    private String category;

    private boolean anonymous;

    public BoardRequestDto(String title, String content, String category, Boolean anonymous) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.anonymous = anonymous;
    }
}

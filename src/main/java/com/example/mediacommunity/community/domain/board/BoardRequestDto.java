package com.example.mediacommunity.community.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String category;

    private Boolean anonymous;

    public BoardRequestDto(String title, String content, String category, Boolean anonymous) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.anonymous = anonymous;
    }
}

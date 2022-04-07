package com.example.mediacommunity.community.domain.board;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class BoardRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String category;

    private Boolean anonymous;

    public BoardRequestDto() {}
    public BoardRequestDto(String title, String content, String category, Boolean anonymous) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.anonymous = anonymous;
    }
}

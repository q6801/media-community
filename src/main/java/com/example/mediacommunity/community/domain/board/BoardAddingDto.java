package com.example.mediacommunity.community.domain.board;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class BoardAddingDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String category;

    private Boolean anonymous;

    public BoardAddingDto() {}
    public BoardAddingDto(String title, String content, String category, Boolean anonymous) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.anonymous = anonymous;
    }
}

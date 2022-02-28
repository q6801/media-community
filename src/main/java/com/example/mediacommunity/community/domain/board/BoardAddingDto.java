package com.example.mediacommunity.community.domain.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
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

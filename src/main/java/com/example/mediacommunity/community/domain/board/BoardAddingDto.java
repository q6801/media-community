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

    public BoardAddingDto() {}
    public BoardAddingDto(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}

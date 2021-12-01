package com.example.mediacommunity.community.domain.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BoardEditingDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;


    public BoardEditingDto() {}
    public BoardEditingDto(String title, String content, String writerId) {
        this.title = title;
        this.content = content;
    }
}

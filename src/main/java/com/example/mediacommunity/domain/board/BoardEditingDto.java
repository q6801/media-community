package com.example.mediacommunity.domain.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BoardEditingDto {
    @NotBlank
    private String content;

    @NotBlank
    private String writerId;

    public BoardEditingDto() {}
    public BoardEditingDto(String content, String writerId) {
        this.content = content;
        this.writerId = writerId;
    }
}

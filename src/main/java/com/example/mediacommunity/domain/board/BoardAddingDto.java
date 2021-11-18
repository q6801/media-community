package com.example.mediacommunity.domain.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;

@Data
public class BoardAddingDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public BoardAddingDto() {}
    public BoardAddingDto(String title, String content, String writerId) {
        this.title = title;
        this.content = content;
    }
}

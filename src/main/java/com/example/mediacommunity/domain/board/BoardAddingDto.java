package com.example.mediacommunity.domain.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;

@Data
public class BoardAddingDto {
    @NotBlank
    private String content;

    @Positive
    private Long writerId;

    public BoardAddingDto() {}
    public BoardAddingDto(String content, Long writerId) {
        this.content = content;
        this.writerId = writerId;
    }
}

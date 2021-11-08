package com.example.mediacommunity.domain.board;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardAddingDto {
    private String content;
    private Long writerId;

    public BoardAddingDto(String content, Long writerId) {
        this.content = content;
        this.writerId = writerId;
    }
}

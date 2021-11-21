package com.example.mediacommunity.domain.reply;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReplyDto {
    private Long id;
    private Long boardId;
    private String content;
    private String replyer;

    public ReplyDto() {}
    public ReplyDto(Long id, Long boardId, String content, String replyer) {
        this.id = id;
        this.boardId = boardId;
        this.content = content;
        this.replyer = replyer;
    }
}

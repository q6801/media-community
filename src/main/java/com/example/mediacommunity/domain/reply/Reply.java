package com.example.mediacommunity.domain.reply;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Reply {
    private Long id;
    private Long boardId;
    private String content;
    private String replyer;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Reply(Long boardId, String content, String replyer) {
        this.boardId = boardId;
        this.content = content;
        this.replyer = replyer;
    }
}

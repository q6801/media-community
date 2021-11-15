package com.example.mediacommunity.domain.board;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Board {
    private Long id;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String writerId;
    private int viewCnt;

    public Board(String content, Timestamp createdAt, Timestamp updatedAt, String writerId, int viewCnt) {
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.writerId = writerId;
        this.viewCnt = viewCnt;
    }
}

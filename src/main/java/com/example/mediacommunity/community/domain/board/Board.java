package com.example.mediacommunity.community.domain.board;

import lombok.*;

import java.sql.Timestamp;

@Data
public class Board {
    private Long id;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String writerId;
    private int viewCnt;
    private String title;

    public Board(String content, Timestamp createdAt, Timestamp updatedAt, String writerId, int viewCnt, String title) {
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.writerId = writerId;
        this.viewCnt = viewCnt;
        this.title = title;
    }
}

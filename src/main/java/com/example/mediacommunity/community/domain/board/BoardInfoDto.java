package com.example.mediacommunity.community.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class BoardInfoDto {
    private Long id;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int viewCnt;
    private String title;
    private String writer;
}

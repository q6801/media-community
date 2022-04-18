package com.example.mediacommunity.community.domain.board;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int viewCnt;
    private int replyCnt;
    private int heartCnt;
    private String title;
    private String writer;
    private boolean anonymous;
    private String category;


    @Override
    public String toString() {
        return "BoardDto{" +
                "id=" + id +
                '}';
    }
}

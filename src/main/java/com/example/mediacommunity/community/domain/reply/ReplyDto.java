package com.example.mediacommunity.community.domain.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class ReplyDto {
    private Long id;
    private String writer;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Override
    public String toString() {
        return "ReplyDto{" +
                "id=" + id +
                '}';
    }
}

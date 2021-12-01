package com.example.mediacommunity.community.domain.reply;

import lombok.Data;

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

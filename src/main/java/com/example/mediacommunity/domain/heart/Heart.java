package com.example.mediacommunity.domain.heart;

import lombok.Data;

@Data
public class Heart {
    private Long id;
    private Long boardId;
    private String memberId;

    public Heart() {}
    public Heart(Long boardId, String memberId) {
        this.boardId = boardId;
        this.memberId = memberId;
    }
}

package com.example.mediacommunity.community.domain.board;

import lombok.Getter;

@Getter
public enum BoardOrderCriterion {
    CREATED("createdAt"), HEARTS("updatedAt"), VIEWS("viewCnt");

    private String code;

    BoardOrderCriterion(String criterion) {
        this.code = criterion;
    }
}

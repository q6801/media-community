package com.example.mediacommunity.community.domain.board;

import lombok.Getter;

@Getter
public enum BoardOrderCriterion {
    CREATED("createdAt"), HEARTS("heartCnt"), VIEWS("viewCnt"), REPLIES("replyCnt");

    private String code;

    BoardOrderCriterion(String criterion) {
        this.code = criterion;
    }
}

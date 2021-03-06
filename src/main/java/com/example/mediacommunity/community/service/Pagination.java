package com.example.mediacommunity.community.service;

import lombok.Getter;

@Getter
public class Pagination {
    private  int onePageBoardsNum = 10;
    private int rangeSize = 7;

    private int page;
    private int totalBoardsNum;
    private int totalPagesNum;
    private int startingBoardNumInPage;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;

    public void pageInfo(int page, int totalBoardsNum) {
        this.page = page;
        this.totalBoardsNum = totalBoardsNum;

        int left = page - (int)Math.ceil((double)rangeSize / 2) + 1;
        this.startPage = (left > 0) ? left: 1;

        this.totalPagesNum = (int) Math.ceil((double)totalBoardsNum / onePageBoardsNum);
        this.endPage = this.startPage + rangeSize - 1;
        this.startingBoardNumInPage = (page - 1) * onePageBoardsNum;
        this.prev = this.startPage != 1;
        this.next = endPage < totalPagesNum;

        if (this.endPage > this.totalPagesNum) {
            this.endPage = this.totalPagesNum;
        }
    }
}

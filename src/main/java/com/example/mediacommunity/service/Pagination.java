package com.example.mediacommunity.service;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Pagination {
    private  int onePageBoardsNum = 7;
    private int rangeSize = 5;

    private int page;
    private int startingRangeNum;
    private int totalBoardsNum;
    private int totalPagesNum;
    private int startingBoardNumInPage;
    private int endPage;
    private boolean prev;
    private boolean next;

    public void pageInfo(int page, int startingRangeNum, int totalBoardsNum) {
        this.page = page;
        this.startingRangeNum = startingRangeNum;
        this.totalBoardsNum = totalBoardsNum;

        this.totalPagesNum = (int) Math.ceil((double)totalBoardsNum / onePageBoardsNum);
        this.endPage = startingRangeNum * rangeSize;
        this.startingBoardNumInPage = (page - 1) * onePageBoardsNum;
        this.prev = (startingRangeNum == 1) ? false : true;
        this.next = (endPage > totalPagesNum) ? false : true;

        if (this.endPage > this.totalPagesNum) {
            this.endPage = this.totalPagesNum;
            this.next = false;
        }
    }
}

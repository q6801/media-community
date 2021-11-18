package com.example.mediacommunity.domain.board;

import com.example.mediacommunity.service.Pagination;

import java.util.List;

public interface BoardRepository {
    Board save(Board board);
    Board findById(Long id);
    List<Board> findByWriterId(String writerId);
    List<Board> findBoards(Pagination pagination);
    List<Board> findAll();
    void update(Long BoardIdx, Board updateParam);
    void increaseViewCnt(Long id, int viewCnt);
    void delete(Long id);
    int getTotalBoardsNum();
}

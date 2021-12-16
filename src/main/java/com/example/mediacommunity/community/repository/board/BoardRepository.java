package com.example.mediacommunity.community.repository.board;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.service.Pagination;

import java.util.List;

public interface BoardRepository {
    Board save(Board board);
    Board findById(Long id);
    List<Board> findByWriterId(String writerId);
    List<Board> findBoards(Pagination pagination);
    List<Board> findAll();
//    void update(Long BoardIdx, Board updateParam);
//    void increaseViewCnt(Long id, int viewCnt);
    void delete(Long id);
    int getTotalBoardsNum();
}

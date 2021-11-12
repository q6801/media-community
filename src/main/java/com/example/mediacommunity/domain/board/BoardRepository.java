package com.example.mediacommunity.domain.board;

import java.util.List;

public interface BoardRepository {
    Board save(Board board);
    Board findById(Long id);
    List<Board> findByWriterId(String writerId);
    List<Board> findAll();
    void update(Long BoardIdx, Board updateParam);
}

package com.example.mediacommunity.domain.board;

import java.util.List;

public interface BoardRepository {
    Board save(Board board);
    Board findById(Long id);
    List<Board> findByWriterId(Long id);
    List<Board> findAll();
}

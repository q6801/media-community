package com.example.mediacommunity.service.board;

import com.example.mediacommunity.domain.board.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    Board save(Board board);
    Optional<Board> findBoard(Long id);
    List<Board> findBoards(String writerId);
    List<Board> findAllBoards();
    void modifyBoard(Long BoardIdx, Board updateParam);
}

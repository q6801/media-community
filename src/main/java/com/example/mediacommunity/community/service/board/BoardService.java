package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardEditingDto;
import com.example.mediacommunity.community.service.Pagination;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    Board save(Board board);
    Optional<Board> findBoard(Long id);
    Board createBoard(Long boardId);
    List<Board> findBoards(String writerId);
    List<Board> findBoards(Pagination pagination);
    List<Board> findAllBoards();
    void modifyBoard(Long BoardIdx, BoardEditingDto updateParam);
    void increaseViewCnt(Long id, int viewCnt);
    void deleteBoard(Long id);
    int getTotalBoardsNum();
}

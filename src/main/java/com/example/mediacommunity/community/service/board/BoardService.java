package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardAddingDto;
import com.example.mediacommunity.community.service.Pagination;

import java.util.List;

public interface BoardService {
    Board save(Board board);
    Board findBoardById(Long id);
//    Board createBoard(Long boardId);
    List<Board> findByWriterId(String writerId);
    List<Board> findBoards(Pagination pagination);
    List<Board> findAllBoards();
    void modifyBoardUsingDto(Long BoardIdx, BoardAddingDto updateParam);
    void increaseViewCnt(Long id, int viewCnt);
    void deleteBoard(Long id);
    int getTotalBoardsNum();
}

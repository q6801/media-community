package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.BoardCategories;
import com.example.mediacommunity.community.domain.BoardCategory;
import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardAddingDto;
import com.example.mediacommunity.community.service.Pagination;

import java.util.List;

public interface BoardService {
    Board save(Board board);
    Board findBoardById(Long id);
//    Board createBoard(Long boardId);
    List<Board> findByWriterId(String writerId);
    List<Board> findBoards(Pagination pagination, String category);
    List<Board> findAllBoards();
    boolean modifyBoardUsingDto(Long BoardIdx, BoardAddingDto updateParam, String memberId);
    void increaseViewCnt(Long id, int viewCnt);
    boolean deleteBoard(Long boardIdx, String memberId);
    int getTotalBoardsNum();
    public BoardCategories findAllCategories();
    BoardCategory findCategory(String categoryId);
}

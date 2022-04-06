package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.board.*;
import com.example.mediacommunity.community.service.Pagination;

import java.util.List;

public interface BoardService {
    Board save(Board board);
    Board findBoardById(Long id);
    List<Board> findByWriterId(String writerId);
    List<Board> findBoards(Pagination pagination, String category, BoardOrderCriterion orderCriterion);
    List<Board> findAllBoards();
    boolean modifyBoardUsingDto(Long BoardIdx, BoardAddingDto updateParam, String memberId);
    Board increaseViewCnt(long id);
    boolean deleteBoard(Long boardIdx, String memberId);
    int getTotalBoardsNum(String category);
    BoardCategoriesDto findAllCategories();
    BoardCategory findCategory(String categoryId);
}

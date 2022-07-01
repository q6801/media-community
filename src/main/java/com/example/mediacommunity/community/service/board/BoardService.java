package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.board.*;
import com.example.mediacommunity.community.domain.category.BoardCategoriesDto;
import com.example.mediacommunity.community.domain.category.BoardCategory;

import java.util.List;

public interface BoardService {
    Board save(BoardRequestDto boardReqDto, String memberId);
    Board findBoardById(Long id);
    List<Board> findByWriterId(String writerId);
    BoardDtos findBoardDtos(int page, String category, BoardOrderCriterion orderCriterion);
    BoardDto modifyBoardUsingDto(Long BoardIdx, BoardRequestDto updateParam, String memberId);
    Board increaseViewCnt(long id);
    void deleteBoard(Long boardIdx, String memberId);
    BoardCategoriesDto findAllCategories();
    BoardCategory findCategory(String categoryId);
}

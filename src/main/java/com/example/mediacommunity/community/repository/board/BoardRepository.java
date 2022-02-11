package com.example.mediacommunity.community.repository.board;

import com.example.mediacommunity.community.domain.board.BoardCategory;
import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.service.Pagination;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Board save(Board board);
    Optional<Board> findBoardById(Long id);
    List<Board> findByWriterId(Member member);
    List<Board> findBoards(Pagination pagination, String category);
    List<Board> findAll();
    void update(Long BoardIdx, Board updateParam);
    void increaseViewCnt(Long id, int viewCnt);
    void delete(Board board);
    int getTotalBoardsNum();
    List<String> findAllCategories();
    BoardCategory findCategory(String categoryId);
}

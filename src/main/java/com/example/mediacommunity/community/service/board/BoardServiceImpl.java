package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.board.*;
import com.example.mediacommunity.community.domain.category.BoardCategoriesDto;
import com.example.mediacommunity.community.domain.category.BoardCategory;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import com.example.mediacommunity.community.service.Pagination;
import com.example.mediacommunity.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final BoardCategoryService boardCategoryService;

    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    @Transactional(readOnly = true)
    public Board findBoardById(Long id) {
        return boardRepository.findBoardById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Board> findByWriterId(String writerId) {
        Member member = memberService.findMemberById(writerId);
        return boardRepository.findByWriterId(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Board> findBoards(Pagination pagination, String category, BoardOrderCriterion orderCriterion) {
        List<Board> boards = boardRepository.findBoards(pagination, category, orderCriterion);
        if (boards==null) {
            return new ArrayList<>();
        }
        return boards;
    }

    @Override
    public boolean modifyBoardUsingDto(Long boardIdx, BoardRequestDto updateParam, String memberId) {
        Member member = memberService.findMemberById(memberId);
        Board board = boardRepository.findBoardById(boardIdx);
        BoardCategory category = boardCategoryService.findById(updateParam.getCategory());

        if (compareUserAndWriter(member, board)) {
            board.updateBoardWithDto(updateParam, category);
            return true;
        }
        return false;
    }

    @Override
    public Board increaseViewCnt(long boardId) {
        Board board = boardRepository.findBoardById(boardId);
        board.increaseViewCnt();
        return board;
    }

    @Override
    public boolean deleteBoard(Long boardIdx, String memberId) {
        Member member = memberService.findMemberById(memberId);
        Board board = boardRepository.findBoardById(boardIdx);

        if (compareUserAndWriter(member, board)) {
            boardRepository.delete(board);
            return true;
        }
        return false;
    }

    private boolean compareUserAndWriter(Member member, Board board) {
        return member != null && member.equals(board.getMember());
    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalBoardsNum(String category) {
        return boardRepository.getTotalBoardsNum(category);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardCategoriesDto findAllCategories() {
        BoardCategoriesDto bc = new BoardCategoriesDto();
        bc.setCategories(boardRepository.findAllCategories());
        return bc;
    }

    @Override
    @Transactional(readOnly = true)
    public BoardCategory findCategory(String categoryId) {
        return boardRepository.findCategory(categoryId);
    }

    @Override
    public BoardDto convertBoardToBoardDto(long boardId) {
        Board board = boardRepository.findBoardById(boardId);
        return board.convertBoardToBoardDto();
    }



}

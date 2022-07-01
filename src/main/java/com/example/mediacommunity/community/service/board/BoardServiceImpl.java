package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;
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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final BoardCategoryService boardCategoryService;

    @Override
    @Transactional
    public Board save(BoardRequestDto boardReqDto, String memberId) {
        Member member = memberService.findMemberById(memberId);
        BoardCategory category = findCategory(boardReqDto.getCategory());
        Board board = Board.convertBoardRequestDtoToBoard(boardReqDto, member, category);
        return boardRepository.save(board);
    }

    @Override
    public Board findBoardById(Long id) {
        return boardRepository.findBoardById(id);
    }


    @Override
    public List<Board> findByWriterId(String writerId) {
        Member member = memberService.findMemberById(writerId);
        return boardRepository.findByWriterId(member);
    }

    @Override
    public BoardDtos findBoardDtos(int page, String category, BoardOrderCriterion orderCriterion) {
        int totalBoardsNum = boardRepository.getTotalBoardsNum(category);
        Pagination pagination = new Pagination();
        pagination.pageInfo(page, totalBoardsNum);

        List<Board> boards = boardRepository.findBoards(pagination, category, orderCriterion);
        if (boards==null) return new BoardDtos(new ArrayList<>(), pagination);

        List<BoardDto> boardInfoDtos = boards.stream()
                .map(Board::convertBoardToBoardDto)
                .collect(Collectors.toList());
        return new BoardDtos(boardInfoDtos, pagination);
    }

    @Override
    @Transactional
    public BoardDto modifyBoardUsingDto(Long boardIdx, BoardRequestDto updateParam, String memberId) {
        Board board = boardRepository.findBoardById(boardIdx);
        BoardCategory category = boardCategoryService.findById(updateParam.getCategory());

        compareUserAndWriter(memberId, board);
        board.updateBoardWithDto(updateParam, category);
        return board.convertBoardToBoardDto();
    }

    @Override
    @Transactional
    public Board increaseViewCnt(long boardId) {
        Board board = boardRepository.findBoardById(boardId);
        board.increaseViewCnt();
        return board;
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardIdx, String memberId) {
        Board board = boardRepository.findBoardById(boardIdx);

        compareUserAndWriter(memberId, board);
        boardRepository.delete(board);
    }

    private void compareUserAndWriter(String memberId, Board board) {
        if(!board.getMember().getLoginId().equals(memberId))
            throw new CustomRuntimeException(ExceptionEnum.NOT_ALLOWED_ACCESS);
    }

    @Override
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

}

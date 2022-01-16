package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardAddingDto;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import com.example.mediacommunity.community.service.Pagination;
import com.example.mediacommunity.community.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class BoardServiceImpl implements BoardService{
    @Autowired
    BoardRepository boardRepository;
    MemberService memberService;

    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public Board findBoardById(Long id) {
        return boardRepository.findBoardById(id).orElseThrow();
    }


    @Override
    public List<Board> findByWriterId(String writerId) {
        Member member = memberService.findMemberById(writerId);
        return boardRepository.findByWriterId(member);
    }

    @Override
    public List<Board> findBoards(Pagination pagination) {
        return boardRepository.findBoards(pagination);
    }

    @Override
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    @Override
    public void modifyBoardUsingDto(Long boardIdx, BoardAddingDto updateParam) {
        Board board = boardRepository.findBoardById(boardIdx).orElseThrow();
        board.updateBoardWithDto(updateParam);
    }

    @Override
    public void increaseViewCnt(Long id, int viewCnt) {
        boardRepository.findBoardById(id)
            .orElseThrow().increaseViewCnt();
    }

    @Override
    public void deleteBoard(Long id) {
        Board board = boardRepository.findBoardById(id).orElseThrow();
        boardRepository.delete(board);
    }

    @Override
    public int getTotalBoardsNum() {
        return boardRepository.getTotalBoardsNum();
    }
}

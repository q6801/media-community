package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardRepository;
import com.example.mediacommunity.community.service.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService{
    @Autowired
    BoardRepository boardRepository;

    @Override
    public Board save(Board board) {
        try {
            return boardRepository.save(board);
        } catch(DataAccessException e) {
            log.warn("class: BoardServiceImpl, method: save, ", e);
            throw new RuntimeException("saveBoard failed");
        }
    }

    @Override
    public Optional<Board> findBoard(Long id) {
        try{
            Board board = boardRepository.findById(id);
            return Optional.ofNullable(board);
        } catch(DataAccessException e) {
            log.warn("class: BoardServiceImpl, method: findBoard, {}", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Board> findBoards(String writerId) {
        try {
            List<Board> boards = boardRepository.findByWriterId(writerId);
            return boards;
        } catch(DataAccessException e) {
            log.warn("class: BoardServiceImpl, method: findBoards, {}", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Board> findBoards(Pagination pagination) {
        return boardRepository.findBoards(pagination);
    }

    @Override
    public List<Board> findAllBoards() {
        try {
            List<Board> boards = boardRepository.findAll();
            return boards;
        } catch(DataAccessException e) {
            log.warn("class: BoardServiceImpl, method: findAllBoards, {}", e);
            return new ArrayList<>();
        }
    }

    @Override
    public void modifyBoard(Long boardIdx, Board updateParam) {
        try {
            boardRepository.update(boardIdx, updateParam);
        } catch(RuntimeException e) {
            log.warn("class: BoardServiceImpl, method: modifyBoard, {}", e);
        }
    }

    @Override
    public void increaseViewCnt(Long id, int viewCnt) {
        try {
            boardRepository.increaseViewCnt(id, viewCnt);
        } catch(RuntimeException e) {
            log.warn("class: BoardServiceImpl, method: increaseViewCnt, {}", e);
        }
    }

    @Override
    public void deleteBoard(Long id) {
        try {
            boardRepository.delete(id);
        } catch(RuntimeException e) {
            log.warn("class: BoardServiceImpl, method: deleteBoard, {}", e);
        }
    }

    @Override
    public int getTotalBoardsNum() {
        return boardRepository.getTotalBoardsNum();
    }
}
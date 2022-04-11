package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.category.BoardCategory;
import com.example.mediacommunity.community.repository.board.BoardCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardCategoryService {
    private final BoardCategoryRepository boardCategoryRepository;

    public void save(BoardCategory boardCategory) {
        boardCategoryRepository.save(boardCategory);
    }

    public BoardCategory findById(String id) {
        return boardCategoryRepository.findById(id);
    }

    public void remove(BoardCategory boardCategory) {
        boardCategoryRepository.remove(boardCategory);
    }
}

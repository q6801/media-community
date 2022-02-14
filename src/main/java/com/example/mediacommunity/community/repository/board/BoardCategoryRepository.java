package com.example.mediacommunity.community.repository.board;

import com.example.mediacommunity.community.domain.board.BoardCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BoardCategoryRepository {
    private final EntityManager em;

    public void save(BoardCategory boardCategory) {
        em.persist(boardCategory);
    }

    public BoardCategory findById(String id) {
        return em.find(BoardCategory.class, id);
    }

    public void remove(BoardCategory boardCategory) {
        em.remove(boardCategory);
    }
}

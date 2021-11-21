package com.example.mediacommunity.service.heart;

import com.example.mediacommunity.domain.heart.Heart;

import java.util.List;
import java.util.Optional;

public interface HeartService {
    Optional<Heart> findTheHeart(Heart heart);
    /**
     * @param heart
     * @return 좋아요 true, 안좋아요는 false
     */
    Boolean toggleTheHeart(Heart heart);
    List<Heart> findLikingBoards(String memberId);
    List<Heart> findLikingMembers(Long boardId);
    Long cntHearts(Long boardId);
}

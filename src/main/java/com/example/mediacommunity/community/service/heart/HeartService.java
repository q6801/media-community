package com.example.mediacommunity.community.service.heart;

import com.example.mediacommunity.community.domain.heart.Heart;

import java.util.List;
import java.util.Optional;

public interface HeartService {
    Optional<Heart> findTheHeart(Long boardId, String memberId);
    Boolean toggleTheHeart(Long boardId, String MemberId);
    List<Heart> findLikingBoards(String memberId);
    List<Heart> findLikingMembers(Long boardId);
    Long cntHearts(Long boardId);
}

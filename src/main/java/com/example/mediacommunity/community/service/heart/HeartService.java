package com.example.mediacommunity.community.service.heart;

import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.heart.HeartInfoDto;

import java.util.List;
import java.util.Optional;

public interface HeartService {
    Optional<Heart> findTheHeart(Long boardId, String memberId);
    HeartInfoDto toggleTheHeart(Long boardId, String MemberId);
    List<Heart> findLikingBoards(String memberId);
    List<Heart> findLikingMembers(Long boardId);
}

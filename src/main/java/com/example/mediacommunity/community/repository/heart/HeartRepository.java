package com.example.mediacommunity.community.repository.heart;

import com.example.mediacommunity.community.domain.heart.Heart;

import java.util.List;

public interface HeartRepository {
    Heart findTheHeart(Long boardId, String memberId);
    Heart addHeart(Heart heart);
    List<Heart> findLikingBoards(String memberId);
    List<Heart> findLikingMembers(Long boardId);
    Long cntHearts(Long boardId);
    void deleteHeart(Long id);
}

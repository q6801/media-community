package com.example.mediacommunity.community.domain.heart;

import java.util.List;

public interface HeartRepository {
    Heart findTheHeart(Long boardId, String memberId);
    Heart addHeart(Heart heart);
    List<Heart> findLikingBoards(String memberId);
    List<Heart> findLikingMembers(Long boardId);
    Long cntHearts(Long boardId);
    void deleteHeart(Long id);
}

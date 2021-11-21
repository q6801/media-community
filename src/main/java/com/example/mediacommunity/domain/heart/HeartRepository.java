package com.example.mediacommunity.domain.heart;

import com.example.mediacommunity.domain.board.Board;
import com.example.mediacommunity.domain.member.Member;

import java.util.List;

public interface HeartRepository {
    Heart findTheHeart(Heart heart);
    Heart addHeart(Heart heart);
    List<Heart> findLikingBoards(String memberId);
    List<Heart> findLikingMembers(Long boardId);
    Long cntHearts(Long boardId);
    void deleteHeart(Long id);
}

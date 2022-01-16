package com.example.mediacommunity.community.repository.heart;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface HeartRepository {
    Optional<Heart> findTheHeart(Board board, Member member);
    Heart addHeart(Heart heart);
    List<Heart> findLikingBoards(Member member);
    List<Heart> findLikingMembers(Board board);
    Long cntHearts(Board board);
    void deleteHeart(Heart heart);
}

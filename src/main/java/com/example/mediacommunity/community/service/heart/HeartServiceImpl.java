package com.example.mediacommunity.community.service.heart;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.heart.HeartRepository;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService{
    private final HeartRepository heartRepository;
    private final BoardService boardService;
    private final MemberService memberService;

    @Override
    public Optional<Heart> findTheHeart(Long boardId, String memberId) {
        return heartRepository.findTheHeart(boardId, memberId);
    }

    /**
     *
     * @param boardId
     * @param memberId
     * @return heart pushed여부 (true면 이제 누른것)
     */
    @Override
    public Boolean toggleTheHeart(Long boardId, String memberId) {
        try {
            Board board = boardService.findBoard(boardId).orElseThrow();
            Member member = memberService.findMemberById(memberId);
            Optional<Heart> theLikeStatus = findTheHeart(boardId, memberId);

            if (theLikeStatus.isEmpty()) {
                Heart heart = Heart.builder().build();
                heart.setBoard(board);
                heart.setMember(member);
                heartRepository.addHeart(heart);
                return true;
            } else {
                heartRepository.deleteHeart(theLikeStatus.get().getId());
                return false;
            }
        } catch (DataAccessException e) {
            log.warn("class: HeartServiceImpl, method: hitTheLikeButton, {}", e);
            throw new RuntimeException("toggle heart failed");
        }
    }

    @Override
    public List<Heart> findLikingBoards(String memberId) {
        return heartRepository.findLikingBoards(memberId);
    }

    @Override
    public List<Heart> findLikingMembers(Long boardId) {
        return heartRepository.findLikingMembers(boardId);
    }

    @Override
    public Long cntHearts(Long boardId) {
        return heartRepository.cntHearts(boardId);
    }
}

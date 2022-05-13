package com.example.mediacommunity.community.service.heart;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.heart.HeartDto;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.heart.HeartRepository;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @Transactional(readOnly = true)
    public Optional<Heart> findTheHeart(Long boardId, String memberId) {
        Board board = boardService.findBoardById(boardId);
        Member member = memberService.findMemberById(memberId);
        return heartRepository.findTheHeart(board, member);
    }

    /**
     *
     * @param boardId
     * @param memberId
     * @return HeartInfoDto
     */
    @Override
    public HeartDto toggleTheHeart(Long boardId, String memberId) {
        Board board = boardService.findBoardById(boardId);
        Member member = memberService.findMemberById(memberId);
        Optional<Heart> theLikeStatus = heartRepository.findTheHeart(board, member);
        boolean pushed;

        if (theLikeStatus.isEmpty()) {
            Heart heart = new Heart();
            heart.setBoard(board);
            heart.setMember(member);
            heartRepository.addHeart(heart);
            pushed=true;
        } else {
            board.getHearts().remove(theLikeStatus.get());
            heartRepository.deleteHeart(theLikeStatus.get());
            pushed= false;
        }

        return new HeartDto(board.getHeartsCnt(), pushed);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Heart> findLikingBoards(String memberId) {
        Member member = memberService.findMemberById(memberId);
        return heartRepository.findLikingBoards(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Heart> findLikingMembers(Long boardId) {
        Board board = boardService.findBoardById(boardId);
        return heartRepository.findLikingMembers(board);
    }
}

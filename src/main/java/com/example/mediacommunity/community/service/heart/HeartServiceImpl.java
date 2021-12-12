package com.example.mediacommunity.community.service.heart;

import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.heart.HeartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HeartServiceImpl implements HeartService{
    @Autowired
    private HeartRepository heartRepository;

    @Override
    public Optional<Heart> findTheHeart(Long boardId, String memberId) {
        try {
            Heart theLikeStatus = heartRepository.findTheHeart(boardId, memberId);
            return Optional.ofNullable(theLikeStatus);
        } catch(DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean toggleTheHeart(Heart info) {

        try {
            Optional<Heart> theLikeStatus = findTheHeart(info.getBoardId(), info.getMemberId());
            if (theLikeStatus.isEmpty()) {
                heartRepository.addHeart(info);
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

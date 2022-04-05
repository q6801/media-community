package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.UserInfoNotFoundException;
import com.example.mediacommunity.community.domain.heart.HeartInfoDto;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.heart.HeartService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class HeartController {
    private final HeartService heartService;
    private final BoardService boardService;

    @GetMapping("board/{boardIdx}/hearts")
    public HeartInfoDto hearts(@PathVariable long boardIdx, @AuthenticationPrincipal UserInfo userInfo) {
        int cnt = boardService.findBoardById(boardIdx).getHeartCnt();
        Boolean pushed = false;
        if (userInfo != null && heartService.findTheHeart(boardIdx, userInfo.getUsername()).isPresent()) {
            pushed = true;
        }
        return new HeartInfoDto(cnt, pushed);
    }

    @PutMapping("board/{boardIdx}/heart")
    public HeartInfoDto hitTheLikeButton(@AuthenticationPrincipal UserInfo userInfo, @PathVariable Long boardIdx) {
        if (userInfo == null) {
            throw new UserInfoNotFoundException(ExceptionEnum.USER_INFO_NOT_FOUND);
        }
        return heartService.toggleTheHeart(boardIdx, userInfo.getUsername());
    }
}

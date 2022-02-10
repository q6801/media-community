package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.NotAllowedAccessException;
import com.example.mediacommunity.Exception.custom.UserInfoNotFoundException;
import com.example.mediacommunity.community.domain.heart.HeartInfoDto;
import com.example.mediacommunity.community.service.heart.HeartService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @GetMapping("boardInfo/{boardIdx}/hearts")
    public HeartInfoDto hearts(@PathVariable long boardIdx, @AuthenticationPrincipal UserInfo userInfo) {
        Long cnt = heartService.cntHearts(boardIdx);
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
        Boolean pushed = heartService.toggleTheHeart(boardIdx, userInfo.getUsername());
        Long cnt = heartService.cntHearts(boardIdx);
        return new HeartInfoDto(cnt, pushed);
    }
}

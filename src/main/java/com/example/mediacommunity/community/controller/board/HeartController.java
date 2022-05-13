package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.UserInfoNotFoundException;
import com.example.mediacommunity.community.domain.heart.HeartDto;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.heart.HeartService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import com.example.mediacommunity.utils.ApiResult;
import com.example.mediacommunity.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class HeartController {
    private final HeartService heartService;
    private final BoardService boardService;

    @GetMapping("board/{boardId}/hearts")
    public ApiResult<HeartDto> hearts(@PathVariable long boardId, @AuthenticationPrincipal UserInfo userInfo) {
        int heartsCnt = boardService.findBoardById(boardId).getHeartsCnt();
        Boolean pushed = false;
        if (userInfo != null && heartService.findTheHeart(boardId, userInfo.getUsername()).isPresent()) {
            pushed = true;
        }
        return ApiUtils.success(new HeartDto(heartsCnt, pushed));
    }

    @PutMapping("board/{boardId}/heart")
    public ApiResult<HeartDto> hitTheLikeButton(@AuthenticationPrincipal UserInfo userInfo, @PathVariable Long boardId) {
        if (userInfo == null) {
            throw new UserInfoNotFoundException(ExceptionEnum.USER_INFO_NOT_FOUND);
        }
        HeartDto heartDto = heartService.toggleTheHeart(boardId, userInfo.getUsername());
        return ApiUtils.success(heartDto);
    }
}

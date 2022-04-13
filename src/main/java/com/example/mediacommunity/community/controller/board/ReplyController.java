package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.UserInfoNotFoundException;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.domain.reply.ReplyDto;
import com.example.mediacommunity.community.domain.reply.ReplyRequestDto;
import com.example.mediacommunity.community.service.reply.ReplyService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import com.example.mediacommunity.utils.ApiResult;
import com.example.mediacommunity.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("board/{boardIdx}/replies")
    public ApiResult<List<ReplyDto>> replies(@PathVariable long boardIdx) {
        List<Reply> allReplies = replyService.findAllReplies(boardIdx);
        List<ReplyDto> replyDtos = allReplies.stream().map(reply -> reply.convertReplyToReplyInfoDto())
                .collect(Collectors.toList());
        return ApiUtils.success(replyDtos);
    }
    
    @PostMapping("board/{boardId}/reply")
    public ApiResult<ReplyDto> addReply(@RequestBody ReplyRequestDto replyDto, @PathVariable Long boardId,
                                        @AuthenticationPrincipal UserInfo userInfo) {
        checkUserAccount(userInfo);
        Reply reply = replyService.reply(boardId, userInfo.getUsername(), replyDto.getContent());
        return ApiUtils.success(reply.convertReplyToReplyInfoDto());
    }

    @PutMapping("reply/{replyId}")
    public ApiResult<ReplyDto> putReply(@RequestBody ReplyRequestDto replyDto,
                                        @AuthenticationPrincipal UserInfo userInfo, @PathVariable Long replyId) {
        checkUserAccount(userInfo);
        Reply reply = replyService.modifyReply(replyId, replyDto, userInfo.getUsername());
        return ApiUtils.success(reply.convertReplyToReplyInfoDto());
    }


    @DeleteMapping("reply/{replyId}")
    public ApiResult<?> deleteReply(@AuthenticationPrincipal UserInfo userInfo, @PathVariable Long replyId) {
        checkUserAccount(userInfo);
        replyService.deleteReply(replyId);
        return ApiUtils.success(null);
    }

    private void checkUserAccount(@AuthenticationPrincipal UserInfo userInfo) {
        if (userInfo == null) {
            throw new UserInfoNotFoundException(ExceptionEnum.USER_INFO_NOT_FOUND);
        }
    }

}

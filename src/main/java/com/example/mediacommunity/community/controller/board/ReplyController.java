package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.UserInfoNotFoundException;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.domain.reply.ReplyDto;
import com.example.mediacommunity.community.domain.reply.ReplyRequestDto;
import com.example.mediacommunity.community.service.reply.ReplyService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<ReplyDto> replies(@PathVariable long boardIdx) {
        List<Reply> allReplies = replyService.findAllReplies(boardIdx);
        return allReplies.stream().map(reply -> reply.convertReplyToReplyInfoDto())
                .collect(Collectors.toList());
    }
    
    @PostMapping("board/{boardId}/reply")
    public ReplyDto addReply(@RequestBody ReplyRequestDto replyDto, @PathVariable Long boardId,
                             @AuthenticationPrincipal UserInfo userInfo) {
        checkUserAccount(userInfo);
        Reply reply = replyService.reply(boardId, userInfo.getUsername(), replyDto.getContent());
        return reply.convertReplyToReplyInfoDto();
    }

    @PutMapping("reply/{replyId}")
    public ReplyDto putReply(@RequestBody ReplyRequestDto replyDto,
                             @AuthenticationPrincipal UserInfo userInfo, @PathVariable Long replyId) {
        checkUserAccount(userInfo);
        Reply reply = replyService.modifyReply(replyId, replyDto, userInfo.getUsername());
        return reply.convertReplyToReplyInfoDto();
    }


    @DeleteMapping("reply/{replyId}")
    public ResponseEntity<?> deleteReply(@AuthenticationPrincipal UserInfo userInfo, @PathVariable Long replyId) {
        checkUserAccount(userInfo);
        replyService.deleteReply(replyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private void checkUserAccount(@AuthenticationPrincipal UserInfo userInfo) {
        if (userInfo == null) {
            throw new UserInfoNotFoundException(ExceptionEnum.USER_INFO_NOT_FOUND);
        }
    }

}

package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.domain.reply.ReplyInfoDto;
import com.example.mediacommunity.community.domain.reply.ReplyInputDto;
import com.example.mediacommunity.community.service.reply.ReplyService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("reply/{boardId}")
    public ReplyInfoDto reply(@RequestBody ReplyInputDto replyDto, @PathVariable Long boardId,
                                @AuthenticationPrincipal UserInfo userInfo) {
        Reply reply = replyService.reply(boardId, userInfo.getUsername(), replyDto.getContent());
        return reply.convertReplyToReplyInfoDto();
    }

    @GetMapping("boardInfo/{boardIdx}/replies")
    public List<ReplyInfoDto> replies(@PathVariable long boardIdx) {
        List<Reply> allReplies = replyService.findAllReplies(boardIdx);
        return allReplies.stream().map(reply -> reply.convertReplyToReplyInfoDto())
                .collect(Collectors.toList());
    }

}

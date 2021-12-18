package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.common.annotation.AuthUser;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.reply.ReplyDto;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.community.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/{boardId}")
    public String reply(@ModelAttribute ReplyDto replyInfo, @RequestParam int page, RedirectAttributes redirectAttributes,
                        @PathVariable Long boardId, @AuthUser Member member) {
        replyService.reply(boardId, member.getLoginId(), replyInfo.getContent());
        redirectAttributes.addAttribute("page", page);
        return "redirect:/boards/{boardId}";
    }
}

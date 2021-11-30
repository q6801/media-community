package com.example.mediacommunity.controller.board;

import com.example.mediacommunity.annotation.AuthUser;
import com.example.mediacommunity.constant.SessionConst;
import com.example.mediacommunity.domain.member.Member;
import com.example.mediacommunity.domain.reply.Reply;
import com.example.mediacommunity.domain.reply.ReplyDto;
import com.example.mediacommunity.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/{boardIdx}")
    public String reply(@ModelAttribute ReplyDto reply, @RequestParam int page, RedirectAttributes redirectAttributes,
                        @PathVariable Long boardIdx, @AuthUser Member member) {
        replyService.saveReply(new Reply(boardIdx, reply.getContent(), member.getLoginId()));
        redirectAttributes.addAttribute("page", page);
        return "redirect:/boards/{boardIdx}";
    }
}

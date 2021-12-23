package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.community.domain.reply.ReplyDto;
import com.example.mediacommunity.community.service.reply.ReplyService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                        @PathVariable Long boardId, @AuthenticationPrincipal UserInfo userInfo) {
        replyService.reply(boardId, userInfo.getUsername(), replyInfo.getContent());
        redirectAttributes.addAttribute("page", page);
        return "redirect:/boards/{boardId}";
    }
}

package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.common.annotation.AuthUser;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.service.heart.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/heart")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/{boardIdx}")
    public String hitTheLikeButton(@AuthUser Member member, @PathVariable Long boardIdx,
                                   Model model, @RequestParam int page, RedirectAttributes redirectAttributes) {

        if (likeStatus(member, boardIdx)) {
            model.addAttribute("heart", true);
        } else {
            model.addAttribute("heart", false);
        }
        redirectAttributes.addAttribute("page", page);
        return "redirect:/boards/{boardIdx}";
    }

    private Boolean likeStatus(Member member, Long boardIdx) {
        return heartService.toggleTheHeart(new Heart(boardIdx, member.getLoginId()));
    }
}

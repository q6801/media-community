package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.service.heart.HeartService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/heart")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;
    private final MemberService memberService;

    @PostMapping("/{boardIdx}")
    public String hitTheLikeButton(@AuthenticationPrincipal UserInfo userInfo, @PathVariable Long boardIdx,
                                   Model model, @RequestParam int page, RedirectAttributes redirectAttributes) {
        Member authUser = memberService.findMemberById(userInfo.getUsername());
        if (likeStatus(authUser, boardIdx)) {
            model.addAttribute("heart", true);
        } else {
            model.addAttribute("heart", false);
        }
        redirectAttributes.addAttribute("page", page);
        return "redirect:/boards/{boardIdx}";
    }

    private Boolean likeStatus(Member member, Long boardIdx) {
        return heartService.toggleTheHeart(boardIdx, member.getLoginId());
    }
}

package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.community.domain.board.*;
import com.example.mediacommunity.community.domain.category.BoardCategoriesDto;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import com.example.mediacommunity.utils.ApiResult;
import com.example.mediacommunity.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/boards/{category}")
    public ApiResult<BoardDtos> boards(@RequestParam(defaultValue = "1") int page, @PathVariable String category) {
        BoardDtos boardDtos = boardService.findBoardDtos(page, category, BoardOrderCriterion.CREATED);
        return ApiUtils.success(boardDtos);
    }

    @PostMapping("/boards/{category}")
    public ApiResult<BoardDtos> changeBoardsOrder(@RequestParam(defaultValue = "1") int page, @PathVariable String category,
                                      @RequestBody Map<String, String> input) {
        BoardOrderCriterion boardOrderCriterion = BoardOrderCriterion.valueOf(input.get("type"));
        BoardDtos boardDtos = boardService.findBoardDtos(page, category, boardOrderCriterion);
        return ApiUtils.success(boardDtos);
    }

    @GetMapping("/board/{boardIdx}")
    public ApiResult<BoardDto> board(@PathVariable long boardIdx) {
        Board board = boardService.increaseViewCnt(boardIdx);
        return ApiUtils.success(board.convertBoardToBoardDto());
    }

    @PostMapping("/board")
    public ApiResult<Map<String, Long>> addBoard(@Valid @RequestBody BoardRequestDto boardDto, @AuthenticationPrincipal UserInfo userInfo) {
        Board board = boardService.save(boardDto, userInfo.getUsername());
        Map<String, Long> result = new HashMap<>();
        result.put("boardIdx", board.getId());
        return ApiUtils.success(result);
    }

    @PutMapping("/board/{boardIdx}")
    public ApiResult<?> editBoard(@Valid @RequestBody BoardRequestDto boardDto, @PathVariable Long boardIdx,
                                       @AuthenticationPrincipal UserInfo userInfo) {
        boardService.modifyBoardUsingDto(boardIdx, boardDto, userInfo.getUsername());
        return ApiUtils.success(null);
    }


    @DeleteMapping("/board/{boardIdx}")
    public ApiResult<?> deleteBoard(@PathVariable Long boardIdx, @AuthenticationPrincipal UserInfo userInfo) {
        boardService.deleteBoard(boardIdx, userInfo.getUsername());
        return ApiUtils.success(null);
    }

    @GetMapping("/board-category")
    public ApiResult<BoardCategoriesDto> category() {
        return ApiUtils.success(boardService.findAllCategories());
    }
}

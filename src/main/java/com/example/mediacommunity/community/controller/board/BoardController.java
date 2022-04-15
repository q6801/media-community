package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.NotAllowedAccessException;
import com.example.mediacommunity.community.domain.board.*;
import com.example.mediacommunity.community.domain.category.BoardCategoriesDto;
import com.example.mediacommunity.community.domain.category.BoardCategory;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.service.Pagination;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import com.example.mediacommunity.utils.ApiResult;
import com.example.mediacommunity.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class BoardController {

    private final BoardService boardService;
    private final Pagination pagination;
    private final MemberService memberService;

    @GetMapping("/boards/{category}")
    public ApiResult<BoardDtos> boards(@RequestParam(defaultValue = "1") int page, @PathVariable String category) {
        int totalBoardsNum = boardService.getTotalBoardsNum(category);

        pagination.pageInfo(page, totalBoardsNum);
        List<Board> boards = boardService.findBoards(pagination, category, BoardOrderCriterion.CREATED);
        List<BoardDto> boardInfoDtos = boards.stream()
                .map(Board::convertBoardToBoardDto)
                .collect(Collectors.toList());

        BoardDtos boardDtos = new BoardDtos(boardInfoDtos, pagination);
        return ApiUtils.success(boardDtos);
    }

    @PostMapping("/boards/{category}")
    public ApiResult<BoardDtos> changeBoardsOrder(@RequestParam(defaultValue = "1") int page, @PathVariable String category,
                                      @RequestBody Map<String, String> input) {
        int totalBoardsNum = boardService.getTotalBoardsNum(category);
        BoardOrderCriterion boardOrderCriterion = BoardOrderCriterion.valueOf(input.get("type"));

        pagination.pageInfo(page, totalBoardsNum);
        List<Board> boards = boardService.findBoards(pagination, category, boardOrderCriterion);

        List<BoardDto> boardInfoDtos = boards.stream()
                .map(Board::convertBoardToBoardDto)
                .collect(Collectors.toList());

        BoardDtos boardDtos = new BoardDtos(boardInfoDtos, pagination);
        return ApiUtils.success(boardDtos);
    }

    @GetMapping("/board/{boardIdx}")
    public ApiResult<BoardDto> board(@PathVariable long boardIdx) {
        Board board = boardService.increaseViewCnt(boardIdx);
        return ApiUtils.success(board.convertBoardToBoardDto());
    }

    @PostMapping("/board")
    public ApiResult<Map<String, Long>> addBoard(@RequestBody BoardRequestDto boardDto, @AuthenticationPrincipal UserInfo userInfo) {
        Member member = memberService.findMemberById(userInfo.getUsername());
        BoardCategory category = boardService.findCategory(boardDto.getCategory());

        Board board = Board.convertBoardRequestDtoToBoard(boardDto, member, category);
        boardService.save(board);

        Map<String, Long> result = new HashMap<>();
        result.put("boardIdx", board.getId());
        return ApiUtils.success(result);
    }

    @PutMapping("/board/{boardIdx}")
    public ApiResult<?> editBoard(@RequestBody BoardRequestDto boardDto, @PathVariable Long boardIdx,
                                       @AuthenticationPrincipal UserInfo userInfo) {
        if (boardService.modifyBoardUsingDto(boardIdx, boardDto, userInfo.getUsername())) {
            return ApiUtils.success(null);
        }
        throw new NotAllowedAccessException(ExceptionEnum.NOT_ALLOWED_ACCESS);
    }


    @DeleteMapping("/board/{boardIdx}")
    public ApiResult<?> deleteBoard(@PathVariable Long boardIdx, @AuthenticationPrincipal UserInfo userInfo) {
        if(boardService.deleteBoard(boardIdx, userInfo.getUsername())) {
            return ApiUtils.success(null);
        }
        throw new NotAllowedAccessException(ExceptionEnum.NOT_ALLOWED_ACCESS);
    }

    @GetMapping("/board-category")
    public ApiResult<BoardCategoriesDto> category() {
        return ApiUtils.success(boardService.findAllCategories());
    }
}

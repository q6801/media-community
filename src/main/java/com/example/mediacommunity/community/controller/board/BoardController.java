package com.example.mediacommunity.community.controller.board;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.NotAllowedAccessException;
import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardAddingDto;
import com.example.mediacommunity.community.domain.board.BoardInfoDto;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.service.Pagination;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final Pagination pagination;
    private final MemberService memberService;

    @GetMapping("/boards")
    public Map<String, Object> boards(@RequestParam(defaultValue = "1") int page) {
        int totalBoardsNum = boardService.getTotalBoardsNum();

        pagination.pageInfo(page, totalBoardsNum);
        List<Board> boards = boardService.findBoards(pagination);

        List<BoardInfoDto> boardInfoDtos = boards.stream()
                .map(board -> board.convertBoardToBoardInfoDto())
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("boards", boardInfoDtos);
        map.put("pagination", pagination);
        return map;
    }

    @GetMapping("/boardInfo/{boardIdx}")
    public BoardInfoDto board(@PathVariable long boardIdx) {
        Board board = boardService.findBoardById(boardIdx);
        boardService.increaseViewCnt(boardIdx, board.getViewCnt());
        return board.convertBoardToBoardInfoDto();
    }

    @PostMapping("/board")
    public ResponseEntity<?> addBoard(@RequestBody BoardAddingDto boardDto, @AuthenticationPrincipal UserInfo userInfo) {
        Member member = memberService.findMemberById(userInfo.getUsername());
        Board board = Board.convertBoardAddingDtoToBoard(boardDto, member);
        boardService.save(board);

        Map<String, Long> result = new HashMap<>();
        result.put("boardIdx", board.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/board/{boardIdx}")
    public ResponseEntity<?> editBoard(@RequestBody BoardAddingDto boardDto, @PathVariable Long boardIdx,
                                       @AuthenticationPrincipal UserInfo userInfo) {
        if (boardService.modifyBoardUsingDto(boardIdx, boardDto, userInfo.getUsername())) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        throw new NotAllowedAccessException(ExceptionEnum.NOT_ALLOWED_ACCESS);
    }


    @DeleteMapping("/board/{boardIdx}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardIdx, @AuthenticationPrincipal UserInfo userInfo) {
        if(boardService.deleteBoard(boardIdx, userInfo.getUsername())) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new NotAllowedAccessException(ExceptionEnum.NOT_ALLOWED_ACCESS);
    }
}

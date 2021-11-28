package com.example.mediacommunity.controller.board;

import com.example.mediacommunity.annotation.AuthUser;
import com.example.mediacommunity.constant.SessionConst;
import com.example.mediacommunity.domain.board.Board;
import com.example.mediacommunity.domain.board.BoardAddingDto;
import com.example.mediacommunity.domain.board.BoardEditingDto;
import com.example.mediacommunity.domain.heart.Heart;
import com.example.mediacommunity.domain.member.Member;
import com.example.mediacommunity.domain.reply.Reply;
import com.example.mediacommunity.domain.reply.ReplyDto;
import com.example.mediacommunity.service.Pagination;
import com.example.mediacommunity.service.board.BoardService;
import com.example.mediacommunity.service.heart.HeartService;
import com.example.mediacommunity.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final Pagination pagination;
    private final HeartService heartService;
    private final ReplyService replyService;

    @GetMapping
    public String boards(Model model, @RequestParam(defaultValue = "1") int page) {
        rgstrBoardsWithPages(page, model);
        return "community/boards";
    }

    private void rgstrBoardsWithPages(int page, Model model) {
        int totalBoardsNum = boardService.getTotalBoardsNum();
        pagination.pageInfo(page, totalBoardsNum);
        List<Board> boards = boardService.findBoards(pagination);
        model.addAttribute("boards", boards);
        model.addAttribute("pagination", pagination);
    }

    @GetMapping("/{boardIdx}")
    public String board(@PathVariable long boardIdx, Model model, @RequestParam(defaultValue = "1") int page,
                        @AuthUser Member member) {

        Board board = boardService.findBoard(boardIdx)
                .orElseThrow(() -> new RuntimeException("board finding error"));
        model.addAttribute("board", board);
        increaseViewCnt(boardIdx, board);
        if (compareUserAndWriter(member, board)) {
            model.addAttribute("editPermission", true);
        }
        insertHeartStatus(boardIdx, model, member);
        rgstrBoardsWithPages(page, model);

        List<Reply> replies = replyService.findAllReplies(boardIdx);
        model.addAttribute("replies", replies);
        model.addAttribute("reply", new ReplyDto());
        return "community/board";
    }

    private void increaseViewCnt(long boardIdx, Board board) {
        boardService.increaseViewCnt(boardIdx, board.getViewCnt());
        board.setViewCnt(board.getViewCnt() + 1);
    }

    private boolean compareUserAndWriter(Member member, Board board) {
        if (member != null  && member.getLoginId().equals(board.getWriterId())) {
            return true;
        }
        return false;
    }

    private void insertHeartStatus(long boardIdx, Model model, Member member) {
        model.addAttribute("heartNums", heartService.cntHearts(boardIdx));
        if (member != null && heartService.findTheHeart(new Heart(boardIdx, member.getLoginId())).isPresent())
            model.addAttribute("heart", true);
        else
            model.addAttribute("heart", false);
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("board", new BoardAddingDto());
        return "community/addBoard";
    }

    @PostMapping("/add")
    public String addBoard(@Valid @ModelAttribute("board") BoardAddingDto boardDto,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "community/addBoard";
        }
        Board savedBoard = saveBoardToDB(boardDto, member);
        redirectAttributes.addAttribute("boardIdx", savedBoard.getId());
        return "redirect:/boards/{boardIdx}";
    }

    private Board saveBoardToDB(BoardAddingDto boardDto, Member member) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board board = new Board(boardDto.getContent(), timestamp, timestamp, member.getLoginId(), 0, boardDto.getTitle());
        return boardService.save(board);
    }

    @GetMapping("/edit/{boardIdx}")
    public String editForm(@PathVariable long boardIdx, Model model,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        Board board = boardService.findBoard(boardIdx).orElseThrow(() -> new RuntimeException("board not found error"));
        if (compareUserAndWriter(member, board)) {
            model.addAttribute("board", board);
            return "community/editBoard";
        }
        return "redirect:/boards";
    }

    @PostMapping("/edit/{boardIdx}")
    public String editBoard(@PathVariable long boardIdx, @Valid @ModelAttribute("board") BoardEditingDto boardDto,
                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "community/editBoard";
        }
        updateBoardToDB(boardIdx, boardDto);
        return "redirect:/boards/{boardIdx}";
    }

    private void updateBoardToDB(long boardIdx, BoardEditingDto boardDto) {
        Board board = boardService.findBoard(boardIdx).orElseThrow(() -> new RuntimeException("board not found error"));
        Timestamp updatedTime = Timestamp.valueOf(LocalDateTime.now().withNano(0));

        board.setUpdatedAt(updatedTime);
        board.setContent(boardDto.getContent());
        board.setTitle(boardDto.getTitle());
        boardService.modifyBoard(boardIdx, board);
    }

    @PostMapping("/delete/{boardIdx}")
    public String deleteBoard(@PathVariable Long boardIdx,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        Board board = boardService.findBoard(boardIdx)
                .orElseThrow(() -> new RuntimeException("board finding error"));

        if (compareUserAndWriter(member, board)) {
            boardService.deleteBoard(boardIdx);
        }
        return "redirect:/boards";
    }
}

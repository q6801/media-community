package com.example.mediacommunity.controller.board;

import com.example.mediacommunity.constant.SessionConst;
import com.example.mediacommunity.domain.board.Board;
import com.example.mediacommunity.domain.board.BoardAddingDto;
import com.example.mediacommunity.domain.board.BoardEditingDto;
import com.example.mediacommunity.domain.board.BoardRepository;
import com.example.mediacommunity.domain.member.Member;
import com.example.mediacommunity.service.board.BoardService;
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

    @GetMapping
    public String boards(Model model) {
        List<Board> boards = boardService.findAllBoards();
        model.addAttribute("boards", boards);
        return "community/boards";
    }

    @GetMapping("/{boardIdx}")
    public String board(@PathVariable long boardIdx, Model model) {
        Board board = boardService.findBoard(boardIdx)
                .orElseThrow(() -> new RuntimeException("board finding error"));
        model.addAttribute("board", board);
        return "community/board";
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
        Board board = new Board(boardDto.getContent(), timestamp, timestamp, member.getLoginId(), 0);
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
        board.setWriterId(boardDto.getWriterId());
        board.setContent(boardDto.getContent());
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

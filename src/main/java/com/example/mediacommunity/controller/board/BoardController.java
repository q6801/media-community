package com.example.mediacommunity.controller.board;

import com.example.mediacommunity.domain.board.Board;
import com.example.mediacommunity.domain.board.BoardAddingDto;
import com.example.mediacommunity.domain.board.BoardRepository;
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

    private final BoardRepository boardRepository;

    @GetMapping
    public String boards(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "community/boards";
    }

    @GetMapping("/{boardIdx}")
    public String board(@PathVariable long boardIdx, Model model) {
        Board board = boardRepository.findById(boardIdx);
        model.addAttribute("board", board);
        return "community/board";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("board", new BoardAddingDto());
        return "community/addBoard";
    }

    @PostMapping("/add")
    public String addBoard(@Valid @ModelAttribute("board") BoardAddingDto boardDto, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "community/addBoard";
        }

        Board savedBoard = saveBoardToDB(boardDto);
        redirectAttributes.addAttribute("boardIdx", savedBoard.getId());
        return "redirect:/boards/{boardIdx}";
    }

    @GetMapping("/edit/{boardIdx}")
    public String editForm(@PathVariable long boardIdx, Model model) {
        model.addAttribute("board", boardRepository.findById(boardIdx));
        return "community/editBoard";
    }

    @PostMapping("/edit/{boardIdx}")
    public String editBoard(@PathVariable long boardIdx, @Valid @ModelAttribute("board") BoardAddingDto boardDto,
                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "community/editBoard";
        }

        updateBoardToDB(boardIdx, boardDto);
        return "redirect:/boards/{boardIdx}";
    }

    private Board saveBoardToDB(BoardAddingDto boardDto) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board board = new Board(boardDto.getContent(), timestamp, timestamp, boardDto.getWriterId(), 0);
        return boardRepository.save(board);
    }

    private void updateBoardToDB(long boardIdx, BoardAddingDto boardDto) {
        Board board = boardRepository.findById(boardIdx);
        Timestamp updatedTime = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        board.setUpdatedAt(updatedTime);
        board.setWriterId(boardDto.getWriterId());
        board.setContent(boardDto.getContent());
        boardRepository.update(boardIdx, board);
    }
}

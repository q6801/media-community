package com.example.mediacommunity.controller.board;

import com.example.mediacommunity.domain.board.Board;
import com.example.mediacommunity.domain.board.BoardAddingDto;
import com.example.mediacommunity.domain.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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
        return "community/addBoard";
    }

    @PostMapping("/add")
    public String addBoard(@ModelAttribute("boardDto") BoardAddingDto boardDto, RedirectAttributes redirectAttributes) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board board = new Board(boardDto.getContent(), timestamp, timestamp, boardDto.getWriterId(), 0);
        Board savedBoard = boardRepository.save(board);
        redirectAttributes.addAttribute("boardIdx", savedBoard.getId());
        return "redirect:/boards/{boardIdx}";
    }

    @GetMapping("/edit/{boardIdx}")
    public String editForm(@PathVariable long boardIdx, Model model) {
        model.addAttribute("board", boardRepository.findById(boardIdx));
        return "community/editBoard";
    }

    @PostMapping("/edit/{boardIdx}")
    public String editBoard(@PathVariable long boardIdx, @ModelAttribute BoardAddingDto boardDto) {
        Board board = boardRepository.findById(boardIdx);

        Timestamp updatedTime = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        board.setUpdatedAt(updatedTime);
        board.setWriterId(boardDto.getWriterId());
        board.setContent(boardDto.getContent());

        boardRepository.update(boardIdx, board);
        return "redirect:/boards/{boardIdx}";
    }
}

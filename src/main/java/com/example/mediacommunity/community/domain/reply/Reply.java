package com.example.mediacommunity.community.domain.reply;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Long boardId;
    private String content;
//    private String replyer;
    @UpdateTimestamp
    private Timestamp createdAt;
    @CreationTimestamp
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "replyer")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    public void setReplyer(Member member) {
        if (this.member != null) {
            this.member.getReplies().remove(this);
        }
        this.member = member;
        member.getReplies().add(this);   // 주인이 아니라서 저장 시 사용 안됨
    }

    public void setBoard(Board board) {
        if (this.board != null) {
            this.board.getReplies().remove(this);
        }
        this.board = board;
        board.getReplies().add(this);   // 주인이 아니라서 저장 시 사용 안됨
    }

    public static Reply createReply(Member member, Board board, String content) {
        Reply reply = new Reply();
        reply.setReplyer(member);
        reply.setBoard(board);
        reply.content = content;
        return reply;
    }

    public Reply(String content) {
//        this.boardId = boardId;
        this.content = content;
//        this.replyer = replyer;
    }
}

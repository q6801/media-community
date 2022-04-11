package com.example.mediacommunity.community.domain.reply;

import com.example.mediacommunity.community.domain.BaseTimeEntity;
import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replyer")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    public Reply(String content) {
        this.content = content;
    }

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
        Reply reply = new Reply(content);
        reply.setReplyer(member);
        reply.setBoard(board);
        return reply;
    }

    public ReplyDto convertReplyToReplyInfoDto() {
        return new ReplyDto(this.id, this.member.getNickname(), this.content, this.createdAt, this.updatedAt);
    }

    public void updateReplyWithDto(ReplyRequestDto replyInputDto) {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        this.content = replyInputDto.getContent();
    }
}

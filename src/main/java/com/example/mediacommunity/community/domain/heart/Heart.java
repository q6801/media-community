package com.example.mediacommunity.community.domain.heart;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude={"member", "board"})
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getHearts().remove(this);
        }
        this.member = member;
        member.getHearts().add(this);   // 주인이 아니라서 저장 시 사용 안됨
    }

    public void setBoard(Board board) {
        if (this.board != null) {
            this.board.getHearts().remove(this);
        }
        this.board = board;
        board.getHearts().add(this);   // 주인이 아니라서 저장 시 사용 안됨
    }
}

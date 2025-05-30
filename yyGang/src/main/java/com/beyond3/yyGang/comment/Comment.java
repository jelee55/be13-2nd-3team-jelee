package com.beyond3.yyGang.comment;

import com.beyond3.yyGang.board.TimeStamped;
import com.beyond3.yyGang.board.entity.Board;
import com.beyond3.yyGang.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    // 대댓글을 위한 자기참조 관계 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private Comment parentComment;


//    @Column(name = "parent_id")
//    private long parentId;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> childComments = new ArrayList<>();

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public void confirmParent(Comment parent){
        this.parentComment = parent;
        parent.addChild(this);
    }

    public void addChild(Comment child){
        childComments.add(child);
    }
}

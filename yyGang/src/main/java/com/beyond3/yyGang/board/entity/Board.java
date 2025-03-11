
package com.beyond3.yyGang.board.entity;

import com.beyond3.yyGang.board.dto.BoardRequestDto;
import com.beyond3.yyGang.board.TimeStamped;
import com.beyond3.yyGang.comment.Comment;
import com.beyond3.yyGang.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Builder
@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
public class Board extends TimeStamped {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long  id;

    @Column(name = "board_title")
    private String title;

    @Column(name = "board_content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments;

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}

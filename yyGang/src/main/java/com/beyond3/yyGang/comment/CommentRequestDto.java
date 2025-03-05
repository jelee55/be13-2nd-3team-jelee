package com.beyond3.yyGang.comment;

import com.beyond3.yyGang.board.entity.Board;
import com.beyond3.yyGang.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CommentRequestDto {

    private String content;
    private Long userId;
    private Long boardId;


    public Comment toEntity(User user, Board board){
        return Comment.builder()
                .content(this.content)
                .board(board)
                .user(user)
                .build();
    }

}

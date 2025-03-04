package com.beyond3.yyGang.board.dto;

import com.beyond3.yyGang.board.entity.Board;
import com.beyond3.yyGang.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {
    private String title;
    private String content;
    private Long userId;

    public Board toEntity(User user){
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .user(user)
                .build();
    }

}

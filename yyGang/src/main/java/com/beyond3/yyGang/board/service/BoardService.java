package com.beyond3.yyGang.board.service;

import com.beyond3.yyGang.board.dto.BoardPageResponseDto;
import com.beyond3.yyGang.board.dto.BoardUpdateRequestDto;
import com.beyond3.yyGang.board.entity.Board;
import com.beyond3.yyGang.board.dto.BoardRequestDto;
import com.beyond3.yyGang.board.dto.BoardResponseDto;

import java.security.Principal;

public interface BoardService {
    void save(BoardRequestDto requestDto, Principal principal);

    BoardPageResponseDto findAll(int page, int size);

    BoardResponseDto update(Principal principal, Long id, BoardUpdateRequestDto requestDto);

    Board findById(Long id);

    void delete(Principal principal, Long id);

    long getBoardLikeCount(Long boardId);
}

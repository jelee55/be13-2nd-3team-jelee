package com.beyond3.yyGang.board.service;

import com.beyond3.yyGang.board.entity.Board;
import com.beyond3.yyGang.board.dto.BoardRequestDto;
import com.beyond3.yyGang.board.dto.BoardResponseDto;

import java.util.List;

public interface BoardService {
    void save(BoardRequestDto requestDto);

    List<BoardResponseDto> findAll();

    BoardResponseDto update(Long id, BoardRequestDto requestDto);

    Board findById(Long id);

    void delete(Long id);
}

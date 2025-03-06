package com.beyond3.yyGang.board.service;

import com.beyond3.yyGang.board.entity.Board;
import com.beyond3.yyGang.board.repository.BoardRepository;
import com.beyond3.yyGang.board.dto.BoardRequestDto;
import com.beyond3.yyGang.board.dto.BoardResponseDto;
import com.beyond3.yyGang.user.domain.User;
import com.beyond3.yyGang.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void save(BoardRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 유저 없음: " + requestDto.getUserId()));

        Board board = requestDto.toEntity(user);

        boardRepository.save(board);
    }

    @Override
    public List<BoardResponseDto> findAll() {
        List<Board> board = boardRepository.findAll();

        return board.stream()
                .map(BoardResponseDto::new)
                .toList();
    }

    @Override
    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글 없음 ::" + id));

        board.update(requestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Override
    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글 없음"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
       if (!boardRepository.existsById(id)){
           throw new IllegalArgumentException("해당 게시글 없음");
       }
       boardRepository.deleteById(id);
    }
}

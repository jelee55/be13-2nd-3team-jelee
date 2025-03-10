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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void save(BoardRequestDto requestDto, Principal principal) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 유저 없음: " + requestDto.getUserId()));

        Board board = requestDto.toEntity(user);

        boardRepository.save(board);
    }

    @Override
    public Page<BoardResponseDto> findAll(int page, int size) {
        List<Board> board = boardRepository.findAll();

        if(page < 0 || size <= 0){
            throw new IllegalArgumentException("page, size가 유효하지 않음");
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Board> boardPage = boardRepository.findAll(pageable);

        if (boardPage.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않음");
        }
        return boardPage.map(BoardResponseDto::new);
    }

    @Override
    @Transactional
    public BoardResponseDto update(Principal principal, Long id, BoardRequestDto requestDto) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(
                () -> new IllegalArgumentException("없어"));


        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글 없음 ::" + id));

        boolean y = user.getUserId() == board.getUser().getUserId();
        if(!y){
            throw new IllegalArgumentException("해당 게시글 작성자 불일치");
        }
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
    public void delete(Principal principal, Long id) {
//        if (!boardRepository.existsById(id)){
//           throw new IllegalArgumentException("해당 게시글 없음");
//        }
        Board board = boardRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("해당 게시글 없음 ::" + id));

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(
            () -> new IllegalArgumentException("없어"));

        boolean y = user.getUserId() == board.getUser().getUserId();
        if(!y){
            throw new IllegalArgumentException("해당 게시글 작성자 불일치");
        }
       boardRepository.deleteById(id);
    }
}

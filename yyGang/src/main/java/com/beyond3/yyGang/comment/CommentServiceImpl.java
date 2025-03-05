package com.beyond3.yyGang.comment;

import com.beyond3.yyGang.board.entity.Board;
import com.beyond3.yyGang.board.repository.BoardRepository;
import com.beyond3.yyGang.user.domain.User;
import com.beyond3.yyGang.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public void save(Long boardId, CommentRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글 없음"));

        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저 없으"));

        Comment comment = requestDto.toEntity(user, board);

        commentRepository.save(comment);

    }

    @Override
    @Transactional
    public void update(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없음"));
        comment.update(requestDto);
    }

    @Override
    public List<CommentResponseDto> getComments(Long boardId) {
        return commentRepository.findByBoardId(boardId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!commentRepository.existsById(id)){
            throw new IllegalArgumentException("해당 댓글 없음");
        }
        commentRepository.deleteById(id);
    }


}

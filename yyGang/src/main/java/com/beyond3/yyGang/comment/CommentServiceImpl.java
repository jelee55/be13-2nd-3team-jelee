package com.beyond3.yyGang.comment;

import com.beyond3.yyGang.board.entity.Board;
import com.beyond3.yyGang.board.repository.BoardRepository;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public void save(Principal principal, Long boardId, CommentRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글 없음"));
        System.out.println("usergetname::::"+ principal.getName());
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저 없으"));
        log.info("user", user);
        Comment comment = requestDto.toEntity(user, board);

        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void update(Principal principal, Long id, CommentRequestDto requestDto) {

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저 없으"));

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없음"));

        boolean x = user.getUserId() == comment.getUser().getUserId();
        if (!x){
            throw new IllegalArgumentException("작성자 불일치");
        }

        comment.update(requestDto);
    }

    @Override
    public Page<CommentResponseDto> getComments(int page, int size, Long boardId) {

        if(page < 0 || size <= 0){
            throw new IllegalArgumentException("댓글이 없음");
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Comment> commentPage = commentRepository.findByBoardId(boardId, pageable);

        if (commentPage.isEmpty()){
            throw new IllegalArgumentException("존재하지 않음");
        }
        return commentPage.map(CommentResponseDto::new);

    }

    @Override
    @Transactional
    public void delete(Principal principal, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없음"));

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저 없으"));

        boolean x = user.getUserId() == comment.getUser().getUserId();
        if (!x){
            throw new IllegalArgumentException("작성자 불일치");
        }
        commentRepository.deleteById(id);
    }


}

package com.beyond3.yyGang.board.repository;

import com.beyond3.yyGang.board.entity.Board;
import com.beyond3.yyGang.board.entity.BoardLike;
import com.beyond3.yyGang.board.entity.BoardLikeId;
import com.beyond3.yyGang.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {
    boolean existsByUserAndBoard(User user, Board board);

    long countByBoard(Board board);
}

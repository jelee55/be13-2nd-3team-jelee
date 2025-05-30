package com.beyond3.yyGang.cart.repository;

import com.beyond3.yyGang.cart.CartOption;
import com.beyond3.yyGang.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartOptionRepository extends JpaRepository<CartOption, Long> {

    @Query("select co from CartOption co where co.cart.cartId = :cartId")
    List<CartOption> findByCartId(@Param("cartId") Long cartId);

}
package com.beyond3.yyGang.order.repository;

import com.beyond3.yyGang.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderID(Long orderId);

    @Query("select o from Order o join fetch o.orderOptions oo join fetch oo.nSupplement ns where o.user.email = :userEmail")
    List<Order> findByUserEmailWithOrderOptions(@Param("userEmail") String userEmail);
}
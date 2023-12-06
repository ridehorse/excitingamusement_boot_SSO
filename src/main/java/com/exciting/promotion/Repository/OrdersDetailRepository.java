package com.exciting.promotion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exciting.entity.OrdersDetail;

@Repository
public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {
}

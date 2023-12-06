package com.exciting.promotion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exciting.entity.Orders;

@Repository
public interface InsertOrderRepository extends JpaRepository<Orders, Integer> {
}

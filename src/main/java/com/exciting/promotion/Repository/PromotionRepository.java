package com.exciting.promotion.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.exciting.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    @Query(value = "select p from Promotion p")
    List<Promotion> selectPromotion();
}
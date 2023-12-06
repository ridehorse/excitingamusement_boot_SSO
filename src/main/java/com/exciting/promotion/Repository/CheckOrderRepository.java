package com.exciting.promotion.Repository;
 
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exciting.entity.Orders;

@Transactional
public interface CheckOrderRepository extends JpaRepository<Orders, Integer>{
	@Modifying
	@Query(value = "UPDATE Orders o"
			+ "		SET o.checkorder = TRUE"
			+ "		WHERE o.order_id = :order_id")
	int checkorders(@Param("order_id") int order_id);
	
	@Modifying
	@Query(value = "UPDATE Orders o"
			+ "		SET o.checkrefund = TRUE"
			+ "		WHERE o.order_id =:order_id")
	int refundOrder(@Param("order_id") int order_id);
}

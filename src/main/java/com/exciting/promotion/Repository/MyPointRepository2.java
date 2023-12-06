package com.exciting.promotion.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exciting.entity.Mypoint;
import com.exciting.login.entity.Member;

@Repository
public interface MyPointRepository2 extends JpaRepository<Mypoint, Integer> {
//	@Query(value = "SELECT a.order_id,a.member_id,a.order_date,a.checkorder,a.checkrefund,a.use_point,b.quantity,c.ticket_name,c.ticket_price,"
//			+ "			d.promotion_content,d.promotion_name,d.promotion_img,d.discount,e.a_name"
//			+ "			FROM orders a"
//			+ "			JOIN orders_detail b"
//			+ "			ON a.order_id = b.order_id"
//			+ "			JOIN ticket c"
//			+ "			ON b.ticket_id = c.ticket_id"
//			+ "			JOIN promotion d"
//			+ "			ON c.promotion_id = d.promotion_id"
//			+ "			JOIN amusement e"
//			+ "			ON e.amuse_id = d.amuse_id"
//			+ "			WHERE a.member_id= :member_id AND b.quantity <> 0"
//			+ "			ORDER BY a.order_id desc" , nativeQuery = true)
//	List<Map<String, Object>> selectMypoint(@Param("member_id") String member_id);
	@Query(value = "SELECT a.order_id,a.member_id,a.order_date,a.checkorder,a.checkrefund,a.use_point,b.quantity,c.ticket_name,c.ticket_price,"
			+ "			d.promotion_content,d.promotion_name,d.promotion_img,d.discount,e.a_name"
			+ "			FROM orders a"
			+ "			JOIN orders_detail b"
			+ "			ON a.order_id = b.order_id"
			+ "			JOIN ticket c"
			+ "			ON b.ticket_id = c.ticket_id"
			+ "			JOIN promotion d"
			+ "			ON c.promotion_id = d.promotion_id"
			+ "			JOIN amusement e"
			+ "			ON e.amuse_id = d.amuse_id"
			+ "			WHERE member_id= :member_id AND b.quantity <> 0"
			+ "			ORDER BY a.order_date desc" , nativeQuery = true)
	List<Map<String, Object>> selectMypoint2(@Param("member_id") String member_id);

}
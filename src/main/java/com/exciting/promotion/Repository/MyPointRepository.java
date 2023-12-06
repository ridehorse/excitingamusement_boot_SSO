package com.exciting.promotion.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exciting.entity.Mypoint;
import com.exciting.login.entity.Member;

@Repository
public interface MyPointRepository extends JpaRepository<Mypoint, Integer> {
	@Query(value = "SELECT COALESCE(SUM(m.m_point), 0) AS sum_point, m.member_id, m.point_id, m.m_point, m.order_id "
			+ "FROM mypoint m "
			+ "WHERE m.member_id = :member_id "
			+ "GROUP BY m.member_id,m.point_id", nativeQuery = true)
	List<Mypoint> selectmypoint1(@Param("member_id") String member_id);
}
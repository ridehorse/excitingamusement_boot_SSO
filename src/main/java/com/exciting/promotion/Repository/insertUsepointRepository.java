package com.exciting.promotion.Repository;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exciting.entity.Mypoint;
import com.exciting.login.entity.Member;
@Transactional
public interface insertUsepointRepository extends JpaRepository<Mypoint, Integer>{
	@Modifying
	@Query(value = "INSERT INTO mypoint (m_point, order_id, member_id) " +
			"SELECT -1 * :use_point, :order_id, :member_id " +
			"FROM orders " +
			"WHERE use_point IS NOT NULL " +
			"ORDER BY order_id DESC " +
			"LIMIT 1", nativeQuery = true)
	int insertUsePoint(@Param("use_point") BigDecimal use_point, 
			@Param("order_id") int order_id, 
			@Param("member_id") Member member_id);
	
	@Modifying
	@Query(value = "INSERT INTO mypoint(m_point,order_id,member_id) "
			+ " VALUES(:m_point,:order_id,:member_id) ", nativeQuery = true)
	int insertMypoint(@Param("m_point") BigDecimal m_point, 
			@Param("order_id") int order_id, 
			@Param("member_id") Member member_id);
}

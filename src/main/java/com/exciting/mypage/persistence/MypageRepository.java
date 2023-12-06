package com.exciting.mypage.persistence;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exciting.login.dto.MypageDTO;
import com.exciting.login.entity.Member;

public interface MypageRepository extends JpaRepository<Member, String>{
	
	@Transactional
	@Modifying
    @Query("UPDATE Member m SET m.m_phone = :m_phone,m.m_email = :m_email,m.m_address = :m_address,m.m_birth = :m_birth WHERE m.member_id = :member_id")
    int updateMember(@Param("member_id") String member_id,@Param("m_email") String m_email, @Param("m_birth") String m_birth, @Param("m_address") String m_address, @Param("m_phone") String m_phone);
	
	@Query(value = "SELECT COUNT(*) FROM("
	        + "SELECT member_id, b_title, b_content, postdate FROM inquiry a "
	        + "UNION ALL "
	        + "SELECT member_id, b_title, b_content, postdate FROM board b "
	        + "UNION ALL "
	        + "SELECT member_id, '' as b_title, b_reply as b_content, postdate FROM boardreply c) d "
	        + "WHERE member_id = :member_id", nativeQuery = true)
	int countByMember_id(@Param("member_id") String member_id);
	
	@Query(value = "SELECT * FROM("
	        + "SELECT member_id, inquiry_num as view_id, b_title, b_content, postdate, '문의게시판' as category FROM inquiry a "
	        + "UNION ALL "
	        + "SELECT member_id, board_id as view_id, b_title, b_content, postdate, '자유게시판' as category FROM board b "
	        + "UNION ALL "
	        + "SELECT member_id, board_id as view_id, '' as b_title, b_reply as b_content, postdate, '게시판 댓글' as category FROM boardreply c) d "
	        + "WHERE member_id = :member_id ORDER BY postdate DESC LIMIT :limitNum, :amount", nativeQuery = true)
	List<Object[]> selectDescByMember_id(@Param("member_id") String member_id, @Param("limitNum") int limitNum, @Param("amount") int amount);

	@Query(value = "SELECT d.member_id, d.view_id, d.b_title, d.b_content, d.postdate, d.category " +
	        "FROM (" +
	        "   SELECT a.member_id, a.inquiry_num as view_id, a.b_title, a.b_content, a.postdate, '문의게시판' as category FROM inquiry a " +
	        "   UNION ALL " +
	        "   SELECT b.member_id, b.board_id as view_id, b.b_title, b.b_content, b.postdate, '자유게시판' as category FROM board b " +
	        "   UNION ALL " +
	        "   SELECT c.member_id, c.board_id as view_id, '' as b_title, c.b_reply as b_content, c.postdate, '게시판 댓글' as category FROM boardreply c" +
	        ") d " +
	        "WHERE d.member_id = :member_id " +
	        "ORDER BY d.postdate ASC " +
	        "LIMIT :limitNum, :amount", nativeQuery = true)
	List<Object[]> selectAscByMember_id(@Param("member_id") String member_id, @Param("limitNum") int limitNum, @Param("amount") int amount);

	
}

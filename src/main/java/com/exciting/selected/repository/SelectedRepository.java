package com.exciting.selected.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exciting.entity.SelectedEntity;


public interface SelectedRepository extends JpaRepository<SelectedEntity, Integer>, CustomSelectedRepository{
	@Query("SELECT COUNT(*) FROM SelectedEntity s WHERE s.member_id = :member_id AND s.amuse_id = :amuse_id")
	int check(@Param("member_id") String member_id, @Param("amuse_id") int amuse_id);
	
	@Modifying
	@Query("DELETE FROM SelectedEntity s WHERE s.amuse_id = :amuse_id AND s.member_id = :member_id")
	void delete(@Param("member_id") String member_id, @Param("amuse_id") int amuse_id);
}

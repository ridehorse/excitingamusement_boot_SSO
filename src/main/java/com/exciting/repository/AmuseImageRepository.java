package com.exciting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exciting.entity.AmuseImageEntity;

@Repository
public interface AmuseImageRepository extends JpaRepository<AmuseImageEntity, Integer> {
	
	@Query(value = "select a2.aimg_id as aimg_id, a1.amuse_id as amuse_id, a2.url as url, a2.aimg_name from amusement a1, aimage a2"
			+ " where a1.amuse_id = a2.amuse_id and a1.amuse_id = :amuse_id", nativeQuery = true)
	List<AmuseImageEntity> amuseImgList(@Param(value = "amuse_id") Integer amuse_id);
	
}

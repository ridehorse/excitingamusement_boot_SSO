package com.exciting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exciting.entity.FacilityEntity;

@Repository
public interface FacilityRepository extends JpaRepository<FacilityEntity, Integer> {
	
	@Query(value = "select * from facility where amuse_id = :amuse_id", nativeQuery = true)
	List<FacilityEntity> facAllList(@Param(value = "amuse_id") Integer amuse_id);
}

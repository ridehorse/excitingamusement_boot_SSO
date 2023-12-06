package com.exciting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exciting.entity.RidesEntity;

import lombok.val;

@Repository
public interface RidesRepository extends JpaRepository<RidesEntity, Integer>{
	
	@Query(value = "select * from rides where amuse_id = :amuse_id", nativeQuery = true)
	public List<RidesEntity> ridesListAll(@Param(value = "amuse_id") Integer amuse_id);
	
	@Query(value = "select * from rides where rides_id = :rides_id", nativeQuery = true)
	public RidesEntity getOneRide(@Param(value = "rides_id") Integer rides_id);
}

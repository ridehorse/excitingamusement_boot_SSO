package com.exciting.customerService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.exciting.entity.AnnouncementEntity;

@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Integer>,JpaSpecificationExecutor<AnnouncementEntity> {

	@Query(nativeQuery = true , value="select * from announcement where announcement_num = ?1")
	Optional<AnnouncementEntity> findByAnnouncementNum(int announcement_num);
}

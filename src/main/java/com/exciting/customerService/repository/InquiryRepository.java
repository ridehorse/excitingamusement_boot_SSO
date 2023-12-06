package com.exciting.customerService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.exciting.entity.InquiryEntity;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, Integer>,JpaSpecificationExecutor<InquiryEntity>{

	@Query(value="select * from inquiry where ref = ?1",nativeQuery = true)
	List<Optional<InquiryEntity>> findByRef(int inquiry_num);

}

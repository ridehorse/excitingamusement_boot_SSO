package com.exciting.customerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exciting.entity.FaqEntity;

public interface FaqRepository extends JpaRepository<FaqEntity, Integer>,JpaSpecificationExecutor<FaqEntity>{
	
}

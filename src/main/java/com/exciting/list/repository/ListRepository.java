package com.exciting.list.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exciting.entity.AmusementEntity;

public interface ListRepository extends JpaRepository<AmusementEntity, Integer>, CustomListRepository{

}

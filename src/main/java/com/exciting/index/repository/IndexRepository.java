package com.exciting.index.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IndexRepository extends JpaRepository<com.exciting.entity.AmusementEntity, Integer>, CustomIndexRepository{
}

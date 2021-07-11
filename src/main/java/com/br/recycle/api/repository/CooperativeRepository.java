package com.br.recycle.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.recycle.api.model.Cooperative;

@Repository
public interface CooperativeRepository extends JpaRepository<Cooperative, Long> {

	
	List<Cooperative>  findByUserId(Long id);
}

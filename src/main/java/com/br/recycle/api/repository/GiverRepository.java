package com.br.recycle.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.recycle.api.model.Giver;

@Repository
public interface GiverRepository extends JpaRepository<Giver, Long> {
	
	List<Giver>  findByUserId(Long id);

}

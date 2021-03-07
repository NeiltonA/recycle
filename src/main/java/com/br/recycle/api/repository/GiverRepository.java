package com.br.recycle.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.recycle.api.model.Giver;

@Repository
public interface GiverRepository extends JpaRepository<Giver, Long> {
	
	Optional<Giver>  findByUserId(Long id);

}

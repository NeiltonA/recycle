package com.br.recycle.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.recycle.api.model.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

	Optional<Donation> findByCodigo(String codigo);

}

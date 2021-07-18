package com.br.recycle.api.repository;

import com.br.recycle.api.model.Rate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
	List<Rate> findByDonationId(Long donation);
	List<Rate> findByDonationCooperativeId(Long cooperative);
	
	List<Rate> findByDonationGiverUserId(Long user);
	List<Rate> findByDonationCooperativeUserId(Long user);
}

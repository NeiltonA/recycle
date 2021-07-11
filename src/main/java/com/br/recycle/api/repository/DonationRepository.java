package com.br.recycle.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.recycle.api.model.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    Optional<Donation> findByCode(String code);
    
    List<Donation> findByGiverUserId(Long user);
    
    List<Donation> findByCooperativeUserId(Long user);
    
   

}

package com.br.recycle.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.recycle.api.model.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {


}

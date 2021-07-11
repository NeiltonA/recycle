package com.br.recycle.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.recycle.api.model.Donation;
import com.br.recycle.api.repository.DonationRepository;

/**
 * Classe responsável por realizar os serviços das transações de comunicação
 * com a base de dados, com relação ao estado da doação.
 */
@Service
public class FlowDonationService {

	private DonationService donationService;
	private DonationRepository donationRepository;

	@Autowired
	public FlowDonationService(DonationService donationService, DonationRepository donationRepository) {
		this.donationService = donationService;
		this.donationRepository = donationRepository;
	}
	
	@Transactional
	public void confirm(String code) {
		Donation donation = donationService.findByCodeOrFail(code);
		donation.confirm();
		
		donationRepository.save(donation);
	}
	
	@Transactional
	public void cancel(String code) {
		Donation donation = donationService.findByCodeOrFail(code);
		donation.cancel();
		
		donationRepository.save(donation);
	}
	
	@Transactional
	public void deliver(String code) {
		Donation donation = donationService.findByCodeOrFail(code);
		donation.deliver();
		
		donationRepository.save(donation);
	}
}
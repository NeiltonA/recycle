package com.br.recycle.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.recycle.api.model.Donation;
import com.br.recycle.api.repository.DonationRepository;

@Service
public class FlowDonationService {

	@Autowired
	private DonationService service;

	
	@Autowired
	private DonationRepository repository;

	@Transactional
	public void confirm(String code) {
		Donation donation = service.findByCodeOrFail(code);
		donation.confirm();
		
		repository.save(donation);
	}
	
	@Transactional
	public void cancel(String code) {
		Donation donation = service.findByCodeOrFail(code);
		donation.cancel();
		
		repository.save(donation);
	}
	
	@Transactional
	public void deliver(String code) {
		Donation donation = service.findByCodeOrFail(code);
		donation.deliver();
		
		repository.save(donation);
	}
	
	
}
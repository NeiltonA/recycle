package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.br.recycle.api.model.Donation;
import com.br.recycle.api.payload.DonationDtoOut;
import com.br.recycle.api.payload.DonationInput;

/**
 * Classe responsável por transformar os dados relacionado aos modelos da doação.
 * 
 */
@Component
public class DonationDtoAssembler {

	private ModelMapper modelMapper = new ModelMapper();
	
	public Donation toDomainObject(DonationInput donationInput) {
		return modelMapper.map(donationInput, Donation.class);
	}
	
	public DonationDtoOut toModel(Donation donation) {
		return modelMapper.map(donation, DonationDtoOut.class);
	}
	
	public List<DonationDtoOut> toCollectionModel(List<Donation> donations) {
		return donations.stream()
				.map(donation -> toModel(donation))
				.collect(Collectors.toList());
	}	
}
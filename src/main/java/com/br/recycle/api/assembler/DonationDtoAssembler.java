package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.recycle.api.model.Donation;
import com.br.recycle.api.payload.DonationDtoOut;


@Component
public class DonationDtoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public DonationDtoOut toModel(Donation donation) {
		return modelMapper.map(donation, DonationDtoOut.class);
	}
	
	public List<DonationDtoOut> toCollectionModel(List<Donation> donations) {
		return donations.stream()
				.map(donation -> toModel(donation))
				.collect(Collectors.toList());
	}

	
}

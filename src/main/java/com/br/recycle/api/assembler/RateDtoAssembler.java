package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.recycle.api.model.Rate;
import com.br.recycle.api.payload.RateDtoOut;


@Component
public class RateDtoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public RateDtoOut toModel(Rate rate) {
		return modelMapper.map(rate, RateDtoOut.class);
	}
	
	public List<RateDtoOut> toCollectionModel(List<Rate> rates) {
		return rates.stream()
				.map(rate -> toModel(rate))
				.collect(Collectors.toList());
	}

	
}

package com.br.recycle.api.assembler;

import com.br.recycle.api.model.Rate;
import com.br.recycle.api.payload.RateDtoOut;
import com.br.recycle.api.payload.RateInput;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RateDtoAssembler {

	private ModelMapper modelMapper = new ModelMapper();

	public Rate toDomainObject(RateInput rateInput) {
		return modelMapper.map(rateInput, Rate.class);
	}
	
	public RateDtoOut toModel(Rate rate) {
		return modelMapper.map(rate, RateDtoOut.class);
	}
	
	public List<RateDtoOut> toCollectionModel(List<Rate> rates) {
		return rates.stream()
				.map(rate -> toModel(rate))
				.collect(Collectors.toList());
	}
}

package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.payload.CooperativeDtoOut;


@Component
public class CooperativeDtoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CooperativeDtoOut toModel(Cooperative cooperative) {
		return modelMapper.map(cooperative, CooperativeDtoOut.class);
	}
	
	public List<CooperativeDtoOut> toCollectionModel(List<Cooperative> cooperatives) {
		return cooperatives.stream()
				.map(cooperative -> toModel(cooperative))
				.collect(Collectors.toList());
	}

	
}

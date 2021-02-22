package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.recycle.api.model.Giver;
import com.br.recycle.api.payload.GiverDtoOut;


@Component
public class GiverDtoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public GiverDtoOut toModel(Giver giver) {
		return modelMapper.map(giver, GiverDtoOut.class);
	}
	
	public List<GiverDtoOut> toCollectionModel(List<Giver> givers) {
		return givers.stream()
				.map(giver -> toModel(giver))
				.collect(Collectors.toList());
	}

	
}

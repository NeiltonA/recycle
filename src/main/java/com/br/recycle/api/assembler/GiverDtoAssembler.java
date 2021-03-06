package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.br.recycle.api.model.Giver;
import com.br.recycle.api.payload.GiverDtoOut;

/**
 * Classe responsável por transformar os dados relacionado aos modelos do Doador.
 * 
 */
@Component
public class GiverDtoAssembler {

	private ModelMapper modelMapper = new ModelMapper();
	
	public GiverDtoOut toModel(Giver giver) {
		return modelMapper.map(giver, GiverDtoOut.class);
	}
	
	public List<GiverDtoOut> toCollectionModel(List<Giver> givers) {
		return givers.stream()
				.map(giver -> toModel(giver))
				.collect(Collectors.toList());
	}
}
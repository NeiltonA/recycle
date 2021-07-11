package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.br.recycle.api.bean.CnpjResponseBean;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.payload.CooperativeDtoOut;
import com.br.recycle.api.payload.CooperativeInput;
import com.br.recycle.api.payload.DictionaryCnpj;

/**
 * Classe responsável por transformar os dados relacionado aos modelos da cooperativa
 * 
 */
@Component
public class CooperativeDtoAssembler {

	private ModelMapper modelMapper = new ModelMapper();
	
	public Cooperative toDomainObject(CooperativeInput cooperativeInput) {
		return modelMapper.map(cooperativeInput, Cooperative.class);
	}
	
	public CooperativeDtoOut toModel(Cooperative cooperative) {
		return modelMapper.map(cooperative, CooperativeDtoOut.class);
	}
	
	public List<CooperativeDtoOut> toCollectionModel(List<Cooperative> cooperatives) {
		return cooperatives.stream()
				.map(cooperative -> toModel(cooperative))
				.collect(Collectors.toList());
	}	
	
	public DictionaryCnpj toDictionary(CnpjResponseBean cnpjResponseBean) {
		DictionaryCnpj dictionary = new DictionaryCnpj();
		dictionary.setFantasyName(cnpjResponseBean.getFantasia());
		dictionary.setSocialReason(cnpjResponseBean.getNome());
		dictionary.setSituation(cnpjResponseBean.getSituacao());
		dictionary.setType(cnpjResponseBean.getTipo());
		dictionary.setState(cnpjResponseBean.getUf());

		
		return dictionary;
	}
}
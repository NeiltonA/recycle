package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.br.recycle.api.model.Address;
import com.br.recycle.api.payload.AddressDtoOut;
import com.br.recycle.api.payload.AddressInput;
import com.br.recycle.api.payload.AddressPartialInput;

/**
 * Classe responsável por transformar os dados relacionado aos modelos de Endereço.
 * 
 */
@Component
public class AddressDtoAssembler {

	private ModelMapper modelMapper = new ModelMapper();

	public Address toDomainObject(AddressInput addressInput) {
		return modelMapper.map(addressInput, Address.class);
	}
	
	public Address toDomainPartialObject(AddressPartialInput addressPartialInput) {
		return modelMapper.map(addressPartialInput, Address.class);
	}

	public AddressDtoOut toModel(Address address) {
		return modelMapper.map(address, AddressDtoOut.class);
	}

	public List<AddressDtoOut> toCollectionModel(List<Address> addresss) {
		return addresss.stream().map(address -> toModel(address)).collect(Collectors.toList());
	}
}

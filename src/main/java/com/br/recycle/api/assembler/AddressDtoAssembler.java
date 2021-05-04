package com.br.recycle.api.assembler;

import com.br.recycle.api.model.Address;
import com.br.recycle.api.payload.AddressDtoOut;
import com.br.recycle.api.payload.AddressInput;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressDtoAssembler {

	private ModelMapper modelMapper = new ModelMapper();

	public Address toDomainObject(AddressInput addressInput) {
		return modelMapper.map(addressInput, Address.class);
	}

	public AddressDtoOut toModel(Address address) {
		return modelMapper.map(address, AddressDtoOut.class);
	}


	public List<AddressDtoOut> toCollectionModel(List<Address> addresss) {
		return addresss.stream().map(address -> toModel(address)).collect(Collectors.toList());
	}

}

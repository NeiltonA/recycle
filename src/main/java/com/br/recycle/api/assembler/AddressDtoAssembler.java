package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.br.recycle.api.bean.AddressResponseBean;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.payload.AddressDtoOut;
import com.br.recycle.api.payload.AddressInput;
import com.br.recycle.api.util.Dictionary;

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

	public AddressDtoOut toModel(Address address) {
		return modelMapper.map(address, AddressDtoOut.class);
	}

	public List<AddressDtoOut> toCollectionModel(List<Address> addresss) {
		return addresss.stream().map(address -> toModel(address)).collect(Collectors.toList());
	}

	/**
	 * Classe responsável por realizar a transformação de dados
	 * do Bean de endereço para os dados de Dicionario na saída da 
	 * aplicação.
	 * @param {@code AddressResponseBean} - addressResponseBean
	 * @return {@code Dictionary} - Retorna um mapeamento de dicionário.
	 */
	public Dictionary toDictionary(AddressResponseBean addressResponseBean) {
		Dictionary dictionary = new Dictionary();
		dictionary.setZipCode(addressResponseBean.getCep());
		dictionary.setStreet(addressResponseBean.getLogradouro());
		dictionary.setComplement(addressResponseBean.getComplemento());
		dictionary.setNeighborhood(addressResponseBean.getBairro());
		dictionary.setCity(addressResponseBean.getLocalidade());
		dictionary.setState(addressResponseBean.getUf());
		
		return dictionary;
	}
}

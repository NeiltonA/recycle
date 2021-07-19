package com.br.recycle.api.assembler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.br.recycle.api.bean.AddressResponseBean;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.AddressDtoOut;
import com.br.recycle.api.payload.AddressInput;
import com.br.recycle.api.payload.AddressPartialInput;
import com.br.recycle.api.payload.Dictionary;
import com.br.recycle.api.payload.UserIdInput;
import com.br.recycle.api.utils.RegexCharactersUtils;

/**
 * Classe responsável por transformar os dados relacionado aos modelos de Endereço.
 * 
 */
@Component
public class AddressDtoAssembler {

	private ModelMapper modelMapper = new ModelMapper();

	/**
	 * Método responsável por montar o objeto de entidade para o banco.
	 * Não foi utilizado o modelMapper, porque alguns campos precisavam ser
	 * modificados.
	 * @param addressInput - {@code AddressInput}
	 * @return {@code Address}
	 */
	public Address toDomainObject(AddressInput addressInput) {
		Address address = new Address();
		address.setStreet(addressInput.getStreet());
		address.setNumber(RegexCharactersUtils.removeSpecialCharacters(addressInput.getNumber()));
		address.setComplement(addressInput.getComplement());
		address.setZipCode(RegexCharactersUtils.removeSpecialCharacters(addressInput.getZipCode()));
		address.setNeighborhood(addressInput.getNeighborhood());
		address.setState(addressInput.getState());
		address.setCity(addressInput.getCity());
		address.setUser(toDomainObjectAddressUser(addressInput));
		
		return address;
	}
	
	/**
	 * Método responsável por montar o objeto de usuário
	 * relacinado ao endereço. Não foi usado o modelMapper, 
	 * devido a alguns valores que precisavam ser modificados.
	 * @param addressInput - {@code UserIdInput}
	 * @return {@code User} - user
	 */
	private User toDomainObjectAddressUser(AddressInput addressInput) {
		User user = new User();

		if (Objects.nonNull(addressInput.getUser())) {
			UserIdInput userIdInput = addressInput.getUser();
			user.setId(userIdInput.getId());
		}
		
		return user;
	}

	/**
	 * Método responsável por montar o objeto de entidade para o banco.
	 * Não foi utilizado o modelMapper, porque alguns campos precisavam ser
	 * modificados.
	 * @param addressPartialInput - {@code AddressPartialInput}
	 * @return {@code Address}
	 */
	public Address toDomainPartialObject(AddressPartialInput addressPartialInput) {
		Address address = new Address();
		address.setNumber(RegexCharactersUtils.removeSpecialCharacters(addressPartialInput.getNumber()));
		address.setComplement(addressPartialInput.getComplement());
		return address;
	}

	public AddressDtoOut toModel(Address address) {
		return modelMapper.map(address, AddressDtoOut.class);
	}

	public List<AddressDtoOut> toCollectionModel(List<Address> addresss) {
		return addresss.stream().map(address -> toModel(address)).collect(Collectors.toList());
	}

	/**
	 * Método responsável por converter os dados de um bean de endereço
	 * para os dados de saída de um dicionário de endereços.
	 * 
	 * @param {@code AddressResponseBean} addressResponseBean
	 * @return {@code Dictionary} - Retorna os dados de endereço.
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
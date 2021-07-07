package com.br.recycle.api.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.AddressNotFoundException;
import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.repository.AddressRepository;

/**
 * Classe responsável por realizar os serviços das transações de comunicação
 * com a base de dados relacionado aos endereços.. 
 */
@Service
public class AddressService {

	private static final String MSG_ADDRESS_EM_USO = "Address de código %d não pode ser removida, pois está em uso";
	
	private static final String MSG_ADDRESS_NO_CONTENT = "Não existem endereços cadatrados associado com o código cliente %d";
	private AddressRepository addressRepository;
	
	@Autowired
	public AddressService(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	/**
	 * Método responsável por buscar todos os endereços na base de dados.
	 * @return {@code List<Address>}
	 * 		- Caso a base de dados esteja vazia, retorna que não foi encontrado conteúdo.
	 * 		- Caso esteja preenchida, retorna os endereços cadastrados.
	 */
	
	
	public List<Address> findAll(Long user) {
		List<Address> response;
		if (user !=null) {
			response = addressRepository.findByUserId(user);
		}else {
			response = addressRepository.findAll();
		}
		if (response.isEmpty()) {
			throw new AddressNotFoundException((String.format(MSG_ADDRESS_NO_CONTENT, user)));
		}

		return response;
	}

	@Transactional
	public Address save(Address address) {
		return addressRepository.save(address);
	}

	@Transactional
	public void remove(Long addressId) {
		try {
			addressRepository.deleteById(addressId);
			addressRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new AddressNotFoundException(addressId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ADDRESS_EM_USO, addressId));
		}
	}

	public Address findOrFail(Long addressId) {
		return addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
	}
	
}
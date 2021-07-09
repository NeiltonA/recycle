package com.br.recycle.api.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.AddressNotFoundException;
import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.repository.AddressRepository;

import lombok.extern.log4j.Log4j2;

/**
 * Classe responsável por realizar os serviços das transações de comunicação
 * com a base de dados relacionado aos endereços.. 
 */
@Service
@Log4j2
public class AddressService {

	private static final String MSG_ADDRESS_EM_USO = "Address de código %d não pode ser removida, pois está em uso";
	public static final String CACHE_NAME = "address";
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
	
	@Cacheable(cacheNames = "Address", key="#user")
	public List<Address> findAll(Long user) {
		log.info("Address No cache");
		List<Address> response;
		if (user !=null) {
			response = addressRepository.findByUserId(user);
		}else {
			response = addressRepository.findAll();
		}
		if (response.isEmpty()) {
			throw new NoContentException(null);
		}

		return response;
	}

	@Transactional
	@CacheEvict(cacheNames = "Address", allEntries = true)
	public Address save(Address address) {
		return addressRepository.save(address);
	}
	
	@CachePut(cacheNames = "Address", key = "#address.getId()")
	public Address update(final Address address, Long id) {
		try {
		Optional<Address> add = addressRepository.findById(id);
		if (add.isPresent()) {
			address.setId(add.get().getId());
			addressRepository.save(address);
		}else {
			throw new AddressNotFoundException(
					String.format("Endereço não encontrado!"));
		}
		} catch (DataIntegrityViolationException e) {
			throw new AddressNotFoundException(String.format("Erro ao alterar o endereço"));
		}
		return address;
	}

	@Transactional
	@CacheEvict(cacheNames = "Address", key="#addressId")
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

	@Cacheable(cacheNames = "Address", key="#addressId")
	public Address findOrFail(Long addressId) {
		return addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
	}
	
}
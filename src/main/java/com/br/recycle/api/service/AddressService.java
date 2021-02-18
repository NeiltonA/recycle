package com.br.recycle.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.AddressNotFoundException;
import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.repository.AddressRepository;


@Service
public class AddressService {

	private static final String MSG_ADDRESS_EM_USO = "Address de código %d não pode ser removida, pois está em uso";

	@Autowired
	private AddressRepository repository;

	@Transactional
	public Address save(Address address) {
		return repository.save(address);
	}

	@Transactional
	public void remove(Long addressId) {
		try {
			repository.deleteById(addressId);
			repository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new AddressNotFoundException(addressId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ADDRESS_EM_USO, addressId));
		}
	}

	public Address findOrFail(Long addressId) {
		return repository.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
	}

}
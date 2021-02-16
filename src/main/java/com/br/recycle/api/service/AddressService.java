package com.br.recycle.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.AddressNaoEncontradaException;
import com.br.recycle.api.exception.EntidadeEmUsoException;
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
	public void excluir(Long addressId) {
		try {
			repository.deleteById(addressId);
			repository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new AddressNaoEncontradaException(addressId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ADDRESS_EM_USO, addressId));
		}
	}

	public Address buscarOuFalhar(Long addressId) {
		return repository.findById(addressId).orElseThrow(() -> new AddressNaoEncontradaException(addressId));
	}

}
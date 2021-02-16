package com.br.recycle.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.CooperativeNaoEncontradaException;
import com.br.recycle.api.exception.EntidadeEmUsoException;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.repository.CooperativeRepository;



@Service
public class CooperativeService {

	private static final String MSG_COOPERATIVE_EM_USO = "Cooperative de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CooperativeRepository repository;

	@Transactional
	public Cooperative save(Cooperative cooperative) {
		return repository.save(cooperative);
	}

	@Transactional
	public void excluir(Long cooperativeId) {
		try {
			repository.deleteById(cooperativeId);
			repository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new CooperativeNaoEncontradaException(cooperativeId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COOPERATIVE_EM_USO, cooperativeId));
		}
	}

	public Cooperative buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() -> new CooperativeNaoEncontradaException(id));
	}

}
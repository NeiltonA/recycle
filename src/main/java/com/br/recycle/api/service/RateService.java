package com.br.recycle.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.exception.RatingNotFoundException;
import com.br.recycle.api.model.Rate;
import com.br.recycle.api.repository.RateRepository;



@Service
public class RateService {

	private static final String MSG_COOPERATIVE_EM_USO = "Rate de código %d não pode ser removida, pois está em uso";

	@Autowired
	private RateRepository repository;

	@Transactional
	public Rate save(Rate rate) {
		return repository.save(rate);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			repository.deleteById(id);
			repository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new RatingNotFoundException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_COOPERATIVE_EM_USO, id));
		}
	}

	public Rate buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() -> new RatingNotFoundException(id));
	}

}
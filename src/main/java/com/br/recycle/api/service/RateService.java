package com.br.recycle.api.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.RatingNotFoundException;
import com.br.recycle.api.model.Rate;
import com.br.recycle.api.repository.RateRepository;

/**
 * Classe responsável por realizar os serviços das transações de comunicação
 * com a base de dados. 
 */
@Service
public class RateService {

	private static final String MSG_COOPERATIVE_EM_USO = "Rate de código %d não pode ser removida, pois está em uso";

	private RateRepository rateRepository;

	@Autowired
	public RateService(RateRepository rateRepository) {
		this.rateRepository = rateRepository;
	}
	
	/**
	 * Método responsável por buscar todos os cadastros de avaliação.
	 * @return {@code List<Rate>} - Retorna todos os dados de avaliação.
	 */
	public List<Rate> findAll() {
		List<Rate> rates = rateRepository.findAll();
		validateEmpty(rates);
		
		return rates;
	}

	@Transactional
	public Rate save(Rate rate) {
		return rateRepository.save(rate);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			rateRepository.deleteById(id);
			rateRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new RatingNotFoundException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_COOPERATIVE_EM_USO, id));
		}
	}

	public Rate buscarOuFalhar(Long id) {
		return rateRepository.findById(id).orElseThrow(() -> new RatingNotFoundException(id));
	}

	/**
	 * Método responsável por verificar se a lista de avaliações está vazia.
	 * Caso esteja vazia, retorna que está sem conteúdo.
	 * 
	 * @param {@code <List<Rate>} rates
	 */
	private void validateEmpty(List<Rate> rates) {
		if (rates.isEmpty()) {
			throw new NoContentException("Não existe cadastro de avaliações.");
		}
	}

}
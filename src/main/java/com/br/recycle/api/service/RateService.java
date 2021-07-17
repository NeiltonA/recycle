package com.br.recycle.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.EntityNotFoundException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.RatingNotFoundException;
import com.br.recycle.api.model.Rate;
import com.br.recycle.api.repository.RateRepository;

/**
 * Classe responsável por realizar os serviços das transações de comunicação com
 * a base de dados.
 */
@Service
public class RateService {

	//private static final String MSG_COOPERATIVE_EM_USO = "Rate de código %d não pode ser removida, pois está em uso";

	private RateRepository rateRepository;

	@Autowired
	public RateService(RateRepository rateRepository) {
		this.rateRepository = rateRepository;
	}

	/**
	 * Método responsável por buscar todos os cadastros de avaliação.
	 * 
	 * @return {@code List<Rate>} - Retorna todos os dados de avaliação.
	 */
	public List<Rate> findAll(Long donation) {
		List<Rate> response = new ArrayList<>();
		
		if (Objects.nonNull(donation)) {
			response = rateRepository.findByDonationId(donation);
			return validateEmpty(response);
		} else {
			response = rateRepository.findAll();
			return validateEmpty(response);
		}

	}

	/**
	 * Método responsável por salvar os dados de avalição na base de dados.
	 * 
	 * @param {@code Rate} - rate
	 * @return {@code Rate} - Caso ocorra com sucesso, os dados são salvos na base
	 *         de dados. - Caso ocorra algum erro, é lançado a exceção de Erro
	 *         Interno.
	 */
	@Transactional
	public Rate save(Rate rate) {
		try {
			return rateRepository.save(rate);
		} catch (Exception e) {
			throw new InternalServerException("Ocorreu um erro ao salvar os dados da availiação.");
		}
	}

	/**
	 * Método responsável por buscar os dados de avalição por ID.
	 * 
	 * @param {@code Long} - id
	 * @return {@code Rate} - Caso ocorra com sucesso a busca, retorna os dados da
	 *         avaliação. - Caso não tenha avaliação de acordo com o ID informado,
	 *         retorna que o registro não foi encontrado.
	 */
	public Rate findOrFail(Long id) {
		return rateRepository.findById(id).orElseThrow(() -> new RatingNotFoundException(id));
	}

	/**
	 * Método responsável por atualizar os dados na base de dados da avaliação.
	 * 
	 * @param {@code Long} - id
	 * @param {@code Rate} - rate
	 */
	public void update(Long id, Rate rate) {
		Optional<Rate> rateActual = rateRepository.findById(id);

		if (!rateActual.isPresent()) {
			throw new EntityNotFoundException("A avaliação não pode ser atualizada, porque não existe.");
		}

		rate.setId(rateActual.get().getId());
		rateRepository.save(rate);
	}
	
	public void updatePatch(Long id, Rate rate) {
		Optional<Rate> rateActual = rateRepository.findById(id);

		if (!rateActual.isPresent()) {
			throw new EntityNotFoundException("A avaliação não pode ser atualizada, porque não existe.");
		}

		if (rate.getComment() ==null) {
			rate.setComment(rateActual.get().getComment());
		}
		
		if (rate.getDonation() ==null) {
			rate.setDonation(rateActual.get().getDonation());
		}
	
		if (rate.getNote() ==null) {
			rate.setNote(rateActual.get().getNote());
		}
		
		rate.setId(rateActual.get().getId());
		rateRepository.save(rate);
	}

	/**
	 * Método responsável por realizar a deleção da avaliação de acordo com o ID
	 * informado.
	 * 
	 * @param {@code Long} id
	 */
	public void delete(Long id) {
		findOrFail(id);
		rateRepository.deleteById(id);
	}

	/**
	 * Método responsável por verificar se a lista de avaliações está vazia. Caso
	 * esteja vazia, retorna que está sem conteúdo.
	 * 
	 * @param {@code <List<Rate>} rates
	 * @return 
	 */
	private List<Rate> validateEmpty(List<Rate> rates) {
		if (rates.isEmpty()) {
			throw new NoContentException("Não existe cadastro de avaliações.");
		}
		return rates;
	}
	
//	@Transactional
//	public void excluir(Long id) {
//		try {
//			rateRepository.deleteById(id);
//			rateRepository.flush();
//
//		} catch (EmptyResultDataAccessException e) {
//			throw new RatingNotFoundException(id);
//
//		} catch (DataIntegrityViolationException e) {
//			throw new EntityInUseException(String.format(MSG_COOPERATIVE_EM_USO, id));
//		}
//	}
}
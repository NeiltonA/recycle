package com.br.recycle.api.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.DonationNotFoundException;
import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.repository.DonationRepository;

@Service
public class DonationService {

	private static final String DONATION_IN_USE_MESSAGE = "Doação de código %d não pode ser removida, pois está em uso";

	private DonationRepository donationRepository;

	@Autowired
	public DonationService(DonationRepository donationRepository) {
		this.donationRepository = donationRepository;
	}

	/**
	 * Método responsável por buscar todos os cadastros de doações na base de dados.
	 * 
	 * @return {@code List<Donation>} - Retorna todos os cadastros de doações.
	 */
	public List<Donation> findAll() {
		List<Donation> donations = donationRepository.findAll();
		validateEmpty(donations);

		return donations;
	}

	/**
	 * Método responsável por buscar por id os dados da doação.
	 * 
	 * @param {@code Long} - donationId
	 * @return {@code Donation} - Dados da doação
	 */
	public Donation findOrFail(Long donationId) {
		return donationRepository.findById(donationId)
				.orElseThrow(() -> new DonationNotFoundException(donationId));
	}

	/**
	 * Método responsável por salvar os dados da doação na base de dados.
	 * 
	 * @param {@code Donation} - donation
	 * @return {@code Donation} - Retorna os dados de doação salvo.s
	 */
	@Transactional
	public Donation save(Donation donation) {
		try {
			return donationRepository.save(donation);
		} catch (Exception exception) {
			throw new InternalServerException("Erro ao salvar as dações na base de dados");
		}
	}

	/**
	 * Método responsável por atualizar os dados da doação na base de dados.
	 * 
	 * @param {@code Long} - id
	 * @param {@code Donation} - donation
	 */
	@Transactional
	public void update(Long id, Donation donation) {
		Optional<Donation> donationPresent = donationRepository.findById(id);

		if (!donationPresent.isPresent()) {
			throw new EntityNotFoundException("A doação não pode ser atualizada porque ela não existe.");
		}

		donation.setId(donationPresent.get().getId());
		donationRepository.save(donation);
	}

	/**
	 * Método responsável por buscar os dados na base através do código da doação.
	 * 
	 * @param {@code String} code
	 * @return {@code Donation} - Retorna os dados de uma doação.
	 */
	public Donation findByCodeOrFail(String code) {
		return donationRepository.findByCode(code)
				.orElseThrow(() -> new DonationNotFoundException(code));
	}

	@Transactional
	public void remove(Long id) {

		findOrFail(id);

		try {
			donationRepository.deleteById(id);
			donationRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new DonationNotFoundException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(DONATION_IN_USE_MESSAGE, id));
		}
	}

	/**
	 * Método responsável por verificar se a lista de doações está vazia. Caso
	 * estiver vazia, lança a exceção de sem conteúdo.
	 * 
	 * @param {@code List<Donation>} - donations
	 */
	private void validateEmpty(List<Donation> donations) {
		if (donations.isEmpty()) {
			throw new NoContentException("Não existe cadastro de doações");
		}
	}
}
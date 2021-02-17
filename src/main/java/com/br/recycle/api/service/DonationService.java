package com.br.recycle.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.DonationNaoEncontradaException;
import com.br.recycle.api.exception.EntidadeEmUsoException;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.repository.DonationRepository;



@Service
public class DonationService {

	private static final String MSG_DONATION_EM_USO = "Donation de código %d não pode ser removida, pois está em uso";

	@Autowired
	private DonationRepository repository;

	@Transactional
	public Donation save(Donation donation) {
		return repository.save(donation);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			repository.deleteById(id);
			repository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new DonationNaoEncontradaException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_DONATION_EM_USO, id));
		}
	}

	public Donation buscarOuFalhar(Long donationId) {
		return repository.findById(donationId).orElseThrow(() -> new DonationNaoEncontradaException(donationId));
	}

	public Donation buscarOuFalharByCode(String code) {
		return repository.findByCode(code)
			.orElseThrow(() -> new DonationNaoEncontradaException(code));
	}
}
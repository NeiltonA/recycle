package com.br.recycle.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.DonationNotFoundException;
import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.repository.DonationRepository;

@Service
public class DonationService {

    private static final String DONATION_IN_USE_MESSAGE = "Doação de código %d não pode ser removida, pois está em uso";

    @Autowired
    private DonationRepository repository;

	public List<Donation> findAll(Long user) {
		
		List<Donation> response = new ArrayList<>();
		
		if (Objects.nonNull(user)) {
				response = repository.findByGiverUserId(user);
			if (response.isEmpty()) {
				response = repository.findByCooperativeUserId(user);	
			}
			
			
			return validateEmpty(response);
		} else {
			response = repository.findAll();
			return validateEmpty(response);
		}
	}
    
    @Transactional
    public Donation save(Donation donation) {
        return repository.save(donation);
    }

    @Transactional
    public void remove(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new DonationNotFoundException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(DONATION_IN_USE_MESSAGE, id));
        }
    }

    public Donation findOrFail(Long donationId) {
        return repository.findById(donationId).orElseThrow(() -> new DonationNotFoundException(donationId));
    }

    public Donation findByCodeOrFail(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new DonationNotFoundException(code));
    }
    
	private List<Donation> validateEmpty(List<Donation> donations) {
		if (donations.isEmpty()) {
			throw new NoContentException("A lista de doação está vazia.");
		}
		
		return donations;
	}
}
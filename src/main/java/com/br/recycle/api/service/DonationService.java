package com.br.recycle.api.service;

import com.br.recycle.api.exception.DonationNotFoundException;
import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DonationService {

    private static final String DONATION_IN_USE_MESSAGE = "Doação de código %d não pode ser removida, pois está em uso";

    @Autowired
    private DonationRepository repository;

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
}
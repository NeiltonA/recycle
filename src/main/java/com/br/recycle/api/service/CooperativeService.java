package com.br.recycle.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.CooperativeNotFoundException;
import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.repository.CooperativeRepository;

@Service
public class CooperativeService {

    private static final String COOPERATIVE_IN_USE_MESSAGE = "CooperativA de código %d não pode ser removida, pois está em uso.";

    @Autowired
    private CooperativeRepository repository;

    @Transactional
    public Cooperative save(Cooperative cooperative) {
        return repository.save(cooperative);
    }

    @Transactional
    public void remove(Long cooperativeId) {
        try {
            repository.deleteById(cooperativeId);
            repository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new CooperativeNotFoundException(cooperativeId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(COOPERATIVE_IN_USE_MESSAGE, cooperativeId));
        }
    }

    public Cooperative findOrFail(Long id) {
        return repository.findById(id).orElseThrow(() -> new CooperativeNotFoundException(id));
    }

}
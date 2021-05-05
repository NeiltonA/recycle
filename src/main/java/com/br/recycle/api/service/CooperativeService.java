package com.br.recycle.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.exception.CooperativeNotFoundException;
import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.repository.CooperativeRepository;
import com.br.recycle.api.repository.GiverRepository;

@Service
public class CooperativeService {

    private static final String COOPERATIVE_IN_USE_MESSAGE = "CooperativA de código %d não pode ser removida, pois está em uso.";

    @Autowired
    private CooperativeRepository repository;
    
    @Autowired
    private GiverRepository gvRepository;

    @Transactional
    public Cooperative save(Cooperative cooperative) {
    	
    	

		if (verifyCooperative(cooperative.getUser().getId())) {
			throw new BusinessException(
					String.format("Já existe uma cooperativa cadastrada com o código de usuário % s", cooperative.getUser().getId()));
		}else if(verifyGiver(cooperative.getUser().getId())) {
			throw new BusinessException(
					String.format("já existe um usuário do código (%s)  associado com o Doador", cooperative.getUser().getId()));
		}
    	
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
    
    public boolean verifyCooperative(Long id) {
    	boolean cooperative  = repository.findByUserId(id).isPresent();
        return cooperative;
    }
    public boolean verifyGiver(Long id) {
    	boolean giver  = gvRepository.findByUserId(id).isPresent();
        return giver;
    }

    public Cooperative findOrFail(Long id) {
        return repository.findById(id).orElseThrow(() -> new CooperativeNotFoundException(id));
    }

}
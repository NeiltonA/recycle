package com.br.recycle.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.DonationNotFoundException;
import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.exception.EntityNotFoundException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.repository.DonationRepository;

@Service
public class DonationService {

	private static final String DONATION_IN_USE_MESSAGE = "Doação de código %d não pode ser removida, pois está em uso";

	private DonationRepository donationRepository;
	
	@Autowired
	private FlowDonationService flowDonationService;
	
	@Autowired
	public DonationService(DonationRepository donationRepository) {
		this.donationRepository = donationRepository;
	}

	/**
	 * Método responsável por buscar todas as doações na base de dados. E pode 
	 * buscar a doação fazendo o filtro de id por usuário
	 * @param {@code Long} - user
	 * @return {@code List<Donation>} - Retorna os dados da doação.
	 */
	public List<Donation> findAll(Long user) {
		
		List<Donation> response = new ArrayList<>();
		
		if (Objects.nonNull(user)) {
			response = donationRepository.findByGiverUserId(user);
			
			if (response.isEmpty()) {
				response = donationRepository.findByCooperativeUserId(user);	
			}
			
			return validateEmpty(response);
		} else {
			response = donationRepository.findAll();
			return validateEmpty(response);
		}
	}

	/**
	 * Método responsável por buscar os dados da Doação por ID.
	 * @param {@code Long} - donationId
	 * @return {@code Donation} 
	 * 		- Pode retornar os dados de sucesso através do ID.
	 * 		- Pode retornar uma exceção de 404 caso a doação por ID não seja encontrado.
	 */
    public Donation findOrFail(Long donationId) {
        return donationRepository.findById(donationId)
        		.orElseThrow(() -> new DonationNotFoundException(donationId));
    }
    
	/**
	 * Método responsável por buscar os dados da Doação por codigo.
	 * @param {@code String} - code
	 * @return {@code Donation} 
	 * 		- Pode retornar os dados de sucesso através do codigo.
	 * 		- Pode retornar uma exceção de 404 caso a doação por codigo não seja encontrado.
	 */
    public Donation findByCodeOrFail(String code) {
        return donationRepository.findByCode(code)
                .orElseThrow(() -> new DonationNotFoundException(code));
    }
    
	/**
	 * Método responsável por salvar os dados na base de dados.
	 * @param {@code Donation} - donation
	 * @return {@code Donation} - Retorna os dados salvos na base de dados.
	 */
    @Transactional
    public Donation save(Donation donation) {
    	try {
    		return donationRepository.save(donation);	
		} catch (Exception e) {
			throw new InternalServerException("Erro ao salvar os dados da doação na base de dados.");
		}
    }

    /**
     * Método responsável por buscar os dados da doação por ID e realizar a deleção de dados dela.
     * Caso ocorra algum erro no momento da deleção, é gerado uma exception.
     * @param {@code Long} - id
     */
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
     * Método responsável por atualizar os dados da doação na base de dados.
     * @param {@code Long} id
     * @param {@code Donation} donation
     */
    @Transactional
    public void update(Long id, Donation donation) {
    	Optional<Donation> donationActual = donationRepository.findById(id);
    	
    	if (!donationActual.isPresent()) {
			throw new EntityNotFoundException("A doação não pode ser atualizada porque não existe.");
		}
    	
        donation.setId(donationActual.get().getId());
        donationRepository.save(donation);    
    }
    
   
    public void updatePatch(Long id, Donation donation) {
    	Optional<Donation> donationActual = donationRepository.findById(id);
    	
    	if (!donationActual.isPresent()) {
			throw new EntityNotFoundException("A doação não pode ser atualizada porque não existe.");
		}
    	if (donation.getCode() ==null) {
    		donation.setCode(donationActual.get().getCode());
		}
    	if (donation.getDonorUserName() ==null) {
    		donation.setDonorUserName(donationActual.get().getDonorUserName());
		}
    	if (donation.getAmount() ==null) {
    		donation.setAmount(donationActual.get().getAmount());
		}
    	if (donation.getStorage() ==null) {
    		donation.setStorage(donationActual.get().getStorage());
		}
    	if (donation.getAvailabilityDays() ==null) {
    		donation.setAvailabilityDays(donationActual.get().getAvailabilityDays());
		}
    	if (donation.getAvailabilityPeriod() ==null) {
    		donation.setAvailabilityPeriod(donationActual.get().getAvailabilityPeriod());
		}
    	if (donation.getAddress()==null) {
    		donation.setAddress(donationActual.get().getAddress());
		}
    	if (donation.getCooperative()==null) {
    		donation.setCooperative(donationActual.get().getCooperative());
		}
    	if (donation.getGiver()==null) {
    		donation.setGiver(donationActual.get().getGiver());
		}
    	donation.setDateCancellation(donationActual.get().getDateCancellation());
    	donation.setDateConfirmation(donationActual.get().getDateConfirmation());
    	donation.setDateDelivery(donationActual.get().getDateDelivery());
    	donation.setDateRegister(donationActual.get().getDateRegister());
        donation.setId(donationActual.get().getId());
    
        updateDonation(donation);
        updateDonationConfirm(donation);
       
    }
    
    @Transactional
    private void  updateDonation(Donation donation) {
    	donationRepository.save(donation);  
        
    }
    @Async
    private void  updateDonationConfirm(Donation donation) { 
        flowDonationService.confirm(donation.getCode());
    }
    /**
     * Método responsável por validar se as doações estão vazias e retornar os dados.
     * @param {@code List<Donation>} - donations
     * @return {@code List<Donation>}
     * 		- Caso as listas estejam vazias, retorna uma exceção de 204.
     * 		- Caso a lista esteja preenchida retorna a lista de doações.
     */
	private List<Donation> validateEmpty(List<Donation> donations) {
		if (donations.isEmpty()) {
			throw new NoContentException("A lista de doação está vazia.");
		}
		
		return donations;
	}	
}
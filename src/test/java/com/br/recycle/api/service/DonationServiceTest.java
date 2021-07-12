package com.br.recycle.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.recycle.api.exception.DonationNotFoundException;
import com.br.recycle.api.exception.EntityNotFoundException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.model.AvailabilityDays;
import com.br.recycle.api.model.AvailabilityPeriod;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.model.Storage;
import com.br.recycle.api.repository.DonationRepository;

/**
 * Classe responsável por validar os cenários de testes da classe
 * de serviço do doação.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 11/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class DonationServiceTest {
	
	@Mock
	private DonationRepository donationRepository;
	
	@InjectMocks
	private DonationService donationService;

	/**
	 * Método responsável por validar o cenário de teste da busca de todos as doações.
	 */
	@Test
	public void testFindAllSuccess() {
		given(donationRepository.findAll()).willReturn(getMockDonation());
		
		List<Donation> donations = donationService.findAll(null);
		assertNotNull(donations);
	}
	
	/**
	 * Método responsável por validar o cenário de teste da busca de todos as doações.
	 */
	@Test
	public void testFindAllIdNoContent() {
		given(donationRepository.findByGiverUserId(1L)).willReturn(Collections.emptyList());
		
		assertThrows(NoContentException.class, () -> donationService.findAll(1L));
	}
	
	/**
	 * Método responsável por validar o cenário de teste da busca de todos as doações.
	 */
	@Test
	public void testFindAllByIdGiverSuccess() {
		given(donationRepository.findByGiverUserId(1L)).willReturn(getMockDonation());
		
		List<Donation> donations = donationService.findAll(1L);
		assertNotNull(donations);
	}
	
	/**
	 * Método responsável por validar o cenário de teste da busca de todos as doações.
	 */
	@Test
	public void testFindAllByIdCooperativeSuccess() {
		given(donationRepository.findByGiverUserId(1L)).willReturn(Collections.emptyList());
		given(donationRepository.findByCooperativeUserId(1L)).willReturn(getMockDonation());
		
		List<Donation> donations = donationService.findAll(1L);
		assertNotNull(donations);
	}

	/**
	 * Método responsável por validar o cenário de teste da busca de todos as doações,
	 * mas os dados estão vazios e lança a exceção.
	 */
	@Test
	public void testFindAllNoContent() {
		given(donationRepository.findAll()).willReturn(Collections.emptyList());
		
		assertThrows(NoContentException.class, () -> donationService.findAll(null));
	}
	
	/**
	 * Método responsável por validar o cenário de salvar os dados na base dados.
	 */
	@Test
	public void testSaveSuccess() {
		doReturn(getMockDonationSave()).when(donationRepository).save(getMockDonationSave());
		
		Donation donation = donationService.save(getMockDonationSave());
		assertNotNull(donation);
	}
	
	/**
	 * Método responsável por validar o cenário de salvar os dados na base dados, 
	 * mas ocorre erro ao salvar os dados.
	 */
	@Test
	public void testSaveInternal() {
		doThrow(InternalServerException.class).when(donationRepository).save(getMockDonationSave());
		
		assertThrows(InternalServerException.class, () -> donationService.save(getMockDonationSave()));
	}
	
	/**
	 * Método responsável por validar o cenário de teste da busca de doação por ID.
	 */
	@Test
	public void testFindOrFailSuccess() {
		given(donationRepository.findById(1L)).willReturn(Optional.of(getMockDonationSave()));
		
		Donation donation = donationService.findOrFail(1L);
		assertNotNull(donation);
	}
	
	/**
	 * Método responsável por validar o cenário de teste da busca de doação por ID,
	 * e não existe doação por ID e lança exceção.
	 */
	@Test
	public void testFindOrFailDonationNotFound() {
		given(donationRepository.findById(1L)).willThrow(DonationNotFoundException.class);
		
		assertThrows(DonationNotFoundException.class, () -> donationService.findOrFail(1L));
	}
	
	/**
	 * Método responsável por validar o cenário de atualização do cenário para salvar na base de dados.
	 */
	@Test
	public void testUpdateSuccess() {
		given(donationRepository.findById(1L)).willReturn(Optional.of(getMockDonationSave()));
		doReturn(getMockDonationSave()).when(donationRepository).save(getMockDonationSave());
		
		donationService.update(1L, getMockDonationSave());
	}
	
	/**
	 * Método responsável por validar o cenário de atualização do cenário para salvar na base de dados.
	 * mas ocorre um erro na atualização, porque a doação nao existe na base de dados.
	 */
	@Test
	public void testUpdateEntityNotFound() {
		given(donationRepository.findById(1L)).willReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, () -> donationService.update(1L, getMockDonationSave()));
	}
	
	/**
	 * Método responsável por validar o cenário de teste da busca de doação por código.
	 */
	@Test
	public void testFindOrFailCodeSuccess() {
		given(donationRepository.findByCode("testando")).willReturn(Optional.of(getMockDonationSave()));
		
		Donation donation = donationService.findByCodeOrFail("testando");
		assertNotNull(donation);
	}
	
	/**
	 * Método responsável por validar o cenário de teste da busca de doação por ID,
	 * e não existe doação por código e lança exceção.
	 */
	@Test
	public void testFindOrFailCodeDonationNotFound() {
		given(donationRepository.findByCode("testando")).willThrow(DonationNotFoundException.class);
		
		assertThrows(DonationNotFoundException.class, () -> donationService.findByCodeOrFail("testando"));
	}
	
	/**
	 * Método responsável por validar o cenário de teste da busca de doação por ID,
	 * e não existe doação por código e lança exceção.
	 */
	@Test
	public void testFindOrFailCodeDonationNotFoundEmpty() {
		given(donationRepository.findByCode("testando")).willReturn(Optional.empty());
		
		assertThrows(DonationNotFoundException.class, () -> donationService.findByCodeOrFail("testando"));
	}
	
	private Donation getMockDonationSave() {
		Donation donation = new Donation();
		donation.setId(1L);
		donation.setCode("1678asdfgh");
		donation.setDonorUserName("Juca");
		donation.setAmount(10L);
		donation.setStorage(Storage.PET_BOTTLE);
		donation.setAvailabilityDays(AvailabilityDays.WEEK_DAYS);
		donation.setAvailabilityPeriod(AvailabilityPeriod.ANY_TIME);

		return donation;
	}

	private List<Donation> getMockDonation() {
		Donation donation = new Donation();
		donation.setId(1L);
		donation.setCode("1678asdfgh");
		donation.setDonorUserName("Juca");
		donation.setAmount(10L);
		donation.setStorage(Storage.PET_BOTTLE);
		donation.setAvailabilityDays(AvailabilityDays.WEEK_DAYS);
		donation.setAvailabilityPeriod(AvailabilityPeriod.ANY_TIME);

		return List.of(donation);
	}
}

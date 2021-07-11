package com.br.recycle.api.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.recycle.api.model.Donation;
import com.br.recycle.api.repository.DonationRepository;

/**
 * Classe responsável por mapear os cenários de testes da classe de serviço do estado da doação.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 10/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class FlowDonationServiceTest {

	@Mock
	private DonationService donationService;
	
	@Mock
	private DonationRepository donationRepository;
	
	@InjectMocks
	private FlowDonationService flowDonationService;
	
	/**
	 * Método responsável por validar quando uma doação é confirmada e 
	 * salva na base de dados.
	 */
	@Test
	public void testConfirmSuccess() {
		given(donationService.findByCodeOrFail("1")).willReturn(getMockDonation());
		doReturn(getMockDonation()).when(donationRepository).save(getMockDonation());
		
		flowDonationService.confirm("1");
	}

	/**
	 * Método responsável por validar quando uma doação é cancelada e 
	 * salva na base de dados.
	 */
	@Test
	public void testCancelSuccess() {
		given(donationService.findByCodeOrFail("1")).willReturn(getMockDonation());
		doReturn(getMockDonation()).when(donationRepository).save(getMockDonation());
		
		flowDonationService.cancel("1");
	}
	
	private Donation getMockDonation() {
		Donation donation = new Donation();
		donation.setCode("2");

		return donation;
	}
}

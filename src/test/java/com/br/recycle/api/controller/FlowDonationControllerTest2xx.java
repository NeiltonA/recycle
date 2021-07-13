package com.br.recycle.api.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.recycle.api.service.FlowDonationService;

/**
 * Método responsável por validar os testes da classe de 
 * controller de sucesso da status doação.
 *
 */
@ExtendWith(SpringExtension.class)
public class FlowDonationControllerTest2xx {

	@Mock
	private FlowDonationService flowDonationService;
	
	@InjectMocks
	private FlowDonationController flowDonationController = new FlowDonationController(flowDonationService);
	
	@Test
	public void testConfirmSuccess() {
		flowDonationController.confirm("12345679334567");
		verify(flowDonationService, times(1)).confirm("12345679334567");
	}
	
	@Test
	public void testCancelSuccess() {
		flowDonationController.cancel("12345679334567");
		verify(flowDonationService, times(1)).cancel("12345679334567");
	}
	
	@Test
	public void testDeliverSuccess() {
		flowDonationController.deliver("12345679334567");
		verify(flowDonationService, times(1)).deliver("12345679334567");
	}
}

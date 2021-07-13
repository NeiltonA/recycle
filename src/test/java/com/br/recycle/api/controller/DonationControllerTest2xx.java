package com.br.recycle.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.recycle.api.assembler.DonationDtoAssembler;
import com.br.recycle.api.mock.DonationMock;
import com.br.recycle.api.model.AvailabilityDays;
import com.br.recycle.api.model.AvailabilityPeriod;
import com.br.recycle.api.model.Storage;
import com.br.recycle.api.payload.DonationDtoOut;
import com.br.recycle.api.service.DonationService;

/**
 * Método responsável por validar os testes da classe de 
 * controller de sucesso da doação.
 *
 */
@ExtendWith(SpringExtension.class)
public class DonationControllerTest2xx {

	@Mock
	private DonationService donationService;
	
	@Mock
	private DonationDtoAssembler donationDtoAssembler;
	
	@InjectMocks
	private DonationController donationController = new DonationController(donationService, donationDtoAssembler);
	
	@Test
	public void testGetAllSuccess() {
		given(donationService.findAll(null)).willReturn(DonationMock.getMockCollectionDonation());
		given(donationDtoAssembler.toCollectionModel(DonationMock.getMockCollectionDonation())).willReturn(getMockDonation());
		
		List<DonationDtoOut> donationDtoOuts = donationController.getAll(null);
		assertEquals("Juca Silva", donationDtoOuts.get(0).getDonorUserName());
	}
	
	@Test
	public void testGetAllByIdSuccess() {
		given(donationService.findAll(1L)).willReturn(DonationMock.getMockCollectionDonation());
		given(donationDtoAssembler.toCollectionModel(DonationMock.getMockCollectionDonation())).willReturn(getMockDonation());
		
		List<DonationDtoOut> donationDtoOuts = donationController.getAll(1L);
		assertEquals("Juca Silva", donationDtoOuts.get(0).getDonorUserName());
	}

	@Test
	public void testByIdSuccess() {
		given(donationService.findOrFail(1L)).willReturn(DonationMock.getMockDonation());
		given(donationDtoAssembler.toModel(DonationMock.getMockDonation())).willReturn(getMockDonatioDto());
		
		DonationDtoOut donationDtoOuts = donationController.getById(1L);
		assertEquals("Juca Silva", donationDtoOuts.getDonorUserName());
	}
	
	@Test
	public void testDeleteSuccess() {
		donationController.delete(1L);
		verify(donationService, times(1)).remove(1L);
		
		assertEquals(204, HttpStatus.NO_CONTENT.value());
	}
	
	private DonationDtoOut getMockDonatioDto() {
		DonationDtoOut donationDtoOut = new DonationDtoOut();
        donationDtoOut.setDonorUserName("Juca Silva");
        donationDtoOut.setAmount(10L);
        donationDtoOut.setStorage(Storage.PET_BOTTLE);
        donationDtoOut.setAvailabilityDays(AvailabilityDays.WEEKENDS);
        donationDtoOut.setAvailabilityPeriod(AvailabilityPeriod.MORNING);
        
        return donationDtoOut;
	}

	private List<DonationDtoOut> getMockDonation() {
		DonationDtoOut donationDtoOut = new DonationDtoOut();
        donationDtoOut.setDonorUserName("Juca Silva");
        donationDtoOut.setAmount(10L);
        donationDtoOut.setStorage(Storage.PET_BOTTLE);
        donationDtoOut.setAvailabilityDays(AvailabilityDays.WEEKENDS);
        donationDtoOut.setAvailabilityPeriod(AvailabilityPeriod.MORNING);

        DonationDtoOut donationDtoOut2 = new DonationDtoOut();
        donationDtoOut2.setDonorUserName("Caio Bastos");
        donationDtoOut2.setAmount(50L);
        donationDtoOut2.setStorage(Storage.BARREL);
        donationDtoOut2.setAvailabilityDays(AvailabilityDays.EVERY_DAY);
        donationDtoOut2.setAvailabilityPeriod(AvailabilityPeriod.ANY_TIME);

        return List.of(donationDtoOut, donationDtoOut2);
	}
}

package com.br.recycle.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.br.recycle.api.assembler.RateDtoAssembler;
import com.br.recycle.api.model.Rate;
import com.br.recycle.api.payload.RateDtoOut;
import com.br.recycle.api.service.RateService;

/**
 * Método responsável por validar os testes da classe de 
 * controller de sucesso da avaliação.
 *
 */
@ExtendWith(SpringExtension.class)
public class RateControllerTest2xx {

	@Mock
	private RateService rateService;
	
	@Mock
	private RateDtoAssembler rateDtoAssembler;
	
	@InjectMocks 
	private RateController rateController = new RateController(rateService, rateDtoAssembler);
	
	@Test
	public void testGetAllSuccess() {
		given(rateService.findAll(1L)).willReturn(getMockRates());
		given(rateDtoAssembler.toCollectionModel(getMockRates())).willReturn(getMockRateDtos());
		
		List<RateDtoOut> ratesDtoOuts = rateController.getAll(1L);
		assertNotNull(ratesDtoOuts);
		assertEquals(Long.valueOf(1), ratesDtoOuts.get(0).getId());
	}
	
	@Test
	public void testGetByIdSuccess() {
		given(rateService.findOrFail(1L)).willReturn(getMockRate());
		given(rateDtoAssembler.toModel(getMockRate())).willReturn(getMockRateDto());
		
		RateDtoOut rateDtoOut = rateController.getById(1L);
		assertNotNull(rateDtoOut);
		assertEquals(Long.valueOf(1), rateDtoOut.getId());
	}
	
	/*@Test
	public void testSaveSuccess() {
		given(rateDtoAssembler.toDomainObject(RateMock.getMockRateInput()));
		doReturn(RateMock.getMockModel()).when(rateService).save(RateMock.getMockModel());
		
		rateController.save(RateMock.getMockRateInput());
		
		assertEquals(HttpStatus.CREATED.value(), 201);
	}*/
	
	/*@Test
	public void testUpdateSuccess() {
		given(rateDtoAssembler.toDomainObject(RateMock.getMockRateInput()));
		doReturn(RateMock.getMockModel()).when(rateService).update(1L, getMockRate());
		
		assertEquals(200, HttpStatus.OK.value());
	}*/
	
	@Test
	public void testDeleteSuccess() {
		rateController.delete(1L);
		verify(rateService, times(1)).delete(1L);
		
		assertEquals(204, HttpStatus.NO_CONTENT.value());
	}
	
	private Rate getMockRate() {
		Rate rate = new Rate();
		rate.setId(1L);
		rate.setComment("Otimo");
		rate.setNote(10L);
		//rate.setGiver(GiverMock.getMockToModel());
		//rate.setCooperative(CooperativeMock.getMockCooperative());
		
		return rate;
	}

	private RateDtoOut getMockRateDto() {
		RateDtoOut rateDtoOut = new RateDtoOut();
		rateDtoOut.setId(1L);
		rateDtoOut.setComment("Otimo");
		rateDtoOut.setNote(10L);
		
		return rateDtoOut;
	}
	
	private List<RateDtoOut> getMockRateDtos() {
		RateDtoOut rateDtoOut = new RateDtoOut();
		rateDtoOut.setId(1L);
		rateDtoOut.setComment("Otimo");
		rateDtoOut.setNote(10L);
		
		return List.of(rateDtoOut);
	}

	private List<Rate> getMockRates() {
		Rate rate = new Rate();
		rate.setId(1L);
		rate.setComment("Otimo");
		rate.setNote(10L);
		//rate.setGiver(GiverMock.getMockToModel());
		//rate.setCooperative(CooperativeMock.getMockCooperative());
		
		return List.of(rate);
	}
}

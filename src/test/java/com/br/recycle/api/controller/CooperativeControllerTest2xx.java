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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.recycle.api.assembler.CooperativeDtoAssembler;
import com.br.recycle.api.mock.CooperativeMock;
import com.br.recycle.api.payload.CooperativeDtoOut;
import com.br.recycle.api.payload.DictionaryCnpj;
import com.br.recycle.api.service.CooperativeService;

/**
 * Método responsável por validar os testes da classe de 
 * controller de sucesso do cooperativa.
 *
 */
@ExtendWith(SpringExtension.class)
public class CooperativeControllerTest2xx {
	
	@Mock
	private CooperativeDtoAssembler cooperativeDtoAssembler;
	
	@Mock
	private CooperativeService cooperativeService;
	
	@InjectMocks
	private CooperativeController cooperativeController = new CooperativeController(cooperativeDtoAssembler, cooperativeService);
	
	@Test
	public void testGetAllSuccess() {
		given(cooperativeService.findAll(null)).willReturn(CooperativeMock.getMockCollectionCooperative());
		given(cooperativeDtoAssembler.toCollectionModel(CooperativeMock.getMockCollectionCooperative()))
			.willReturn(getMockCooperatives());
		
		List<CooperativeDtoOut> cooperatives = cooperativeController.getAll(null);
		assertNotNull(cooperatives);
	}
	
	@Test
	public void testGetAllByIdSuccess() {
		given(cooperativeService.findAll(1L)).willReturn(CooperativeMock.getMockCollectionCooperative());
		given(cooperativeDtoAssembler.toCollectionModel(CooperativeMock.getMockCollectionCooperative()))
			.willReturn(getMockCooperatives());
		
		List<CooperativeDtoOut> cooperatives = cooperativeController.getAll(1L);
		assertNotNull(cooperatives);
	}
	
	@Test
	public void testGetCnpjSuccess() {
		given(cooperativeService.searchCnpj("90454562000106")).willReturn(CooperativeMock.getMockDictionaryCnpj());
		given(cooperativeDtoAssembler.toDictionary(CooperativeMock.getMockDictionaryCnpj()))
			.willReturn(getMockCooperativeDictionary());
		
		ResponseEntity<DictionaryCnpj> dictionary = cooperativeController.getCnpj("90454562000106");
		assertNotNull(dictionary);
	}
	
	@Test
	public void testGetByIdSuccess() {
		given(cooperativeService.findOrFail(1L)).willReturn(CooperativeMock.getMockCooperative());
		given(cooperativeDtoAssembler.toModel(CooperativeMock.getMockCooperative()))
			.willReturn(getMockCooperative());
		
		CooperativeDtoOut cooperative = cooperativeController.getById(1L);
		assertNotNull(cooperative);
	}
	
	@Test
	public void testDeleteSuccess() {
		cooperativeController.delete(1L);
		verify(cooperativeService, times(1)).delete(1L);
		
		assertEquals(204, HttpStatus.NO_CONTENT.value());
	}

	private CooperativeDtoOut getMockCooperative() {
		CooperativeDtoOut cooperativeDtoOut = new CooperativeDtoOut();
		cooperativeDtoOut.setCnpj("52288720000106");
		
		return cooperativeDtoOut;
	}

	private DictionaryCnpj getMockCooperativeDictionary() {
		DictionaryCnpj dictionaryCnpj = new DictionaryCnpj();
		dictionaryCnpj.setSocialReason("Empresa fantasia");
		
		return dictionaryCnpj;
	}

	private List<CooperativeDtoOut> getMockCooperatives() {
		CooperativeDtoOut cooperativeDtoOut = new CooperativeDtoOut();
		cooperativeDtoOut.setCnpj("90454562000106");
		
		return List.of(cooperativeDtoOut);
	}

}

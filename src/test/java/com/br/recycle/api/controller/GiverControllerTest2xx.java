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

import com.br.recycle.api.assembler.GiverDtoAssembler;
import com.br.recycle.api.mock.GiverMock;
import com.br.recycle.api.payload.GiverDtoOut;
import com.br.recycle.api.service.GiverService;

/**
 * Método responsável por validar os testes da classe de 
 * controller de sucesso da doador.
 *
 */
@ExtendWith(SpringExtension.class)
public class GiverControllerTest2xx {
	
	@Mock
	private GiverDtoAssembler giverDtoAssembler;
	
	@Mock
	private GiverService giverService;
	
	@InjectMocks
	private GiverController giverController = new GiverController(giverDtoAssembler, giverService);
	
	@Test
	public void testGetAllSuccess() {
		given(giverService.findAll(null)).willReturn(GiverMock.getMockToCollectionModel());
		given(giverDtoAssembler.toCollectionModel(GiverMock.getMockToCollectionModel())).willReturn(getMockGivers());
		
		List<GiverDtoOut> givers = giverController.getAll(null);
		assertNotNull(givers);
		assertEquals(Long.valueOf(1), givers.get(0).getId());
	}
	
	@Test
	public void testGetAllByIdSuccess() {
		given(giverService.findAll(1L)).willReturn(GiverMock.getMockToCollectionModel());
		given(giverDtoAssembler.toCollectionModel(GiverMock.getMockToCollectionModel())).willReturn(getMockGivers());
		
		List<GiverDtoOut> givers = giverController.getAll(1L);
		assertNotNull(givers);
		assertEquals(Long.valueOf(1), givers.get(0).getId());
	}
	
	@Test
	public void testGetByIdSuccess() {
		given(giverService.findOrFail(1L)).willReturn(GiverMock.getMockToModel());
		given(giverDtoAssembler.toModel(GiverMock.getMockToModel())).willReturn(getMockGiver());
		
		GiverDtoOut givers = giverController.getById(1L);
		assertNotNull(givers);
		assertEquals(Long.valueOf(1), givers.getId());
	}

	@Test
	public void testDeleteSuccess() {
		giverController.delete(1L);
		verify(giverService, times(1)).deleteById(1L);
		
		assertEquals(204, HttpStatus.NO_CONTENT.value());
	}
	
	private GiverDtoOut getMockGiver() {
		GiverDtoOut giverDtoOut = new GiverDtoOut();
		giverDtoOut.setId(1L);
		
		return giverDtoOut;
	}

	private List<GiverDtoOut> getMockGivers() {
		GiverDtoOut giverDtoOut = new GiverDtoOut();
		giverDtoOut.setId(1L);
		
		return List.of(giverDtoOut);
	}

}

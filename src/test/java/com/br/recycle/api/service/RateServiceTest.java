package com.br.recycle.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.mock.CooperativeMock;
import com.br.recycle.api.mock.GiverMock;
import com.br.recycle.api.model.Rate;
import com.br.recycle.api.repository.RateRepository;

/**
 * Classe responsável por validar os cenários de testes da classe
 * de serviço do avaliação.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 11/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class RateServiceTest {
	
	@Mock
	private RateRepository rateRepository;
	
	@InjectMocks
	private RateService rateService;
	
	/*
	 * Método responsável por validar o cenário de busca de todos as avalições.
	 */
	@Test
	public void testFindAllSuccess() {
		given(rateRepository.findAll()).willReturn(getMockRates());
		List<Rate> rates = rateService.findAll();
		assertNotNull(rates);
		assertEquals(1, rates.size());
	}

	/*
	 * Método responsável por validar o cenário de busca de todos as avalições,
	 * mas não tem dados na busca
	 */
	@Test
	public void testFindAllNoContent() {
		given(rateRepository.findAll()).willReturn(Collections.emptyList());
		assertThrows(NoContentException.class, () -> rateService.findAll());
	}
	
	private List<Rate> getMockRates() {
		Rate rate = new Rate();
		rate.setId(1L);
		rate.setComment("Otimo");
		rate.setNote(10L);
		rate.setGiver(GiverMock.getMockToModel());
		rate.setCooperative(CooperativeMock.getMockCooperative());
		
		return List.of(rate);
	}

}

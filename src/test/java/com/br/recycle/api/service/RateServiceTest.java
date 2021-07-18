package com.br.recycle.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.recycle.api.exception.EntityNotFoundException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.RatingNotFoundException;
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
		List<Rate> rates = rateService.findAll(null, null);
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
		assertThrows(NoContentException.class, () -> rateService.findAll(null, null));
	}
	
	/*
	 * Método responsável por validar o cenário de busca das avalições
	 * por ID.
	 */
	@Test
	public void testFindOrFailSuccess() {
		given(rateRepository.findById(1L)).willReturn(Optional.of(getMockRate()));
		Rate rate = rateService.findOrFail(1L);
		assertNotNull(rate);
	}
	
	/*
	 * Método responsável por validar o cenário de busca as avalições por ID,
	 * mas não tem dados na busca
	 */
	@Test
	public void testFindAllRatingNotFound() {
		given(rateRepository.findById(1L)).willThrow(RatingNotFoundException.class);
		assertThrows(RatingNotFoundException.class, () -> rateService.findOrFail(1L));
	}
	
	/**
	 * Método responsável por validar o cenário onde os dados de avaliação
	 * é salvo na base de dados.
	 */
	@Test
	public void testSaveSuccess() {
		doReturn(getMockRate()).when(rateRepository).save(getMockRate());
		
		Rate rate = rateService.save(getMockRate());
		assertNotNull(rate);
	}
	
	/**
	 * Método responsável por validar o cenário onde os dados de avaliação
	 * é salvo na base de dados, mas ocorre um erro interno.
	 */
	@Test
	public void testSaveInternalServerError() {
		doThrow(InternalServerException.class).when(rateRepository).save(getMockRate());
		
		assertThrows(InternalServerException.class, () -> rateService.save(getMockRate()));

	}
	
	/*
	 * Método responsável por validar o cenário onde os dados da avaliação
	 * são atualizados.
	 */
	@Test
	public void testUpdateSuccess() {
		given(rateRepository.findById(1L)).willReturn(Optional.of(getMockRate()));
		doReturn(getMockRate()).when(rateRepository).save(getMockRate());
		
		rateService.update(1L, getMockRate());
	}
	
	/*
	 * Método responsável por validar o cenário onde os dados da avaliação
	 * são atualizados.
	 */
	@Test
	public void testUpdateEntityNotFound() {
		given(rateRepository.findById(1L)).willReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, () -> rateService.update(1L, getMockRate()));
	}
	
	/**
	 * Método responsável por validar o cenário de sucess de deleção.
	 */
	@Test
	public void testDeleteByIdSuccess() {
		given(rateRepository.findById(1L)).willReturn(Optional.of(getMockRate()));		
		rateService.delete(1L);
		verify(rateRepository, times(1)).deleteById(1L);
	}
	
	private Rate getMockRate() {
		Rate rate = new Rate();
		rate.setId(1L);
		rate.setComment("Otimo");
		rate.setNote(10L);
		
		return rate;
	}

	private List<Rate> getMockRates() {
		Rate rate = new Rate();
		rate.setId(1L);
		rate.setComment("Otimo");
		rate.setNote(10L);
		
		return List.of(rate);
	}

}

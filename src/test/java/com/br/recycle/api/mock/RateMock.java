package com.br.recycle.api.mock;

import java.util.List;

import com.br.recycle.api.model.Rate;
import com.br.recycle.api.payload.RateInput;

/**
 * Classe de mock para atender os cenários de avaliação entrada
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 06/07/2021
 */

public class RateMock {

	public static RateInput getMockRateInput() {
		RateInput rateInput = new RateInput();
		rateInput.setNote(9L);
		rateInput.setComment("Excelente");
		//rateInput.setCooperative(CooperativeIdInputMock.getMockCooperativeIdInput());
		//rateInput.setGiver(GiverIdInputMock.getMockGiverIdInput());
		
		return rateInput; 
	}

	public static Rate getMockModel() {
		Rate rate = new Rate();
		rate.setId(1L);
		rate.setNote(9L);
		rate.setComment("Excelente");
		rate.setCooperative(CooperativeIdMock.getMockCooperative());
		rate.setGiver(GiverIdMock.getMockGiver());
		
		return rate;
	}

	public static List<Rate> getMockCollectionModel() {
		Rate rate = new Rate();
		rate.setId(1L);
		rate.setNote(9L);
		rate.setComment("Excelente");
		rate.setCooperative(CooperativeIdMock.getMockCooperative());
		rate.setGiver(GiverIdMock.getMockGiver());
		
		return List.of(rate);
	}	
}

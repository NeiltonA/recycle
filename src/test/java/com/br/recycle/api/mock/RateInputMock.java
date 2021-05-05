package com.br.recycle.api.mock;

import com.br.recycle.api.payload.RateInput;

/**
 * Classe de mock para atender os cenários de Rate entrada
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 04/05/2021
 */
public class RateInputMock {

    public static RateInput getMockRateInput() {
        RateInput rateInput = new RateInput();
        rateInput.setNote(9L);
        rateInput.setComment("Excelente");
        rateInput.setCooperative(CooperativeIdInputMock.getMockCooperativeIdInput());
        rateInput.setGiver(GiverIdInputMock.getMockGiverIdInput());

        return rateInput;
    }
}

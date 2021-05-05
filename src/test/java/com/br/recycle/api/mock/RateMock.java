package com.br.recycle.api.mock;

import com.br.recycle.api.model.Rate;

import java.util.Collections;
import java.util.List;

/**
 * Classe de mock para atender os cenários de Rate de resposta
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class RateMock {

    public static Rate getMockRate() {
        Rate rate = new Rate();
        rate.setId(1L);
        rate.setNote(1L);
        rate.setComment("Excelente");
        rate.setCooperative(CooperativeIdMock.getMockCooperative());
        rate.setGiver(GiverIdMock.getMockGiver());

        return rate;
    }

    public static List<Rate> getMockCollectionRate() {
        Rate rate = new Rate();
        rate.setId(1L);
        rate.setNote(1L);
        rate.setComment("Excelente");
        rate.setCooperative(CooperativeIdMock.getMockCooperative());
        rate.setGiver(GiverIdMock.getMockGiver());

        return Collections.singletonList(rate);
    }
}

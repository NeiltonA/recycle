package com.br.recycle.api.mock;

import com.br.recycle.api.model.AvailabilityDays;
import com.br.recycle.api.model.AvailabilityPeriod;
import com.br.recycle.api.model.Storage;
import com.br.recycle.api.payload.DonationInput;

/**
 * Classe de mock para atender os cenários de Doação entrada
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class DonationInputMock {

    public static DonationInput getMockDonationInput() {
        DonationInput donationInput = new DonationInput();
        donationInput.setDonorUserName("Juca Silva");
        donationInput.setAmount(10L);
        donationInput.setStorage(Storage.PET_BOTTLE);
        donationInput.setAvailabilityDays(AvailabilityDays.WEEKENDS);
        donationInput.setAvailabilityPeriod(AvailabilityPeriod.MORNING);
        donationInput.setGiver(GiverIdInputMock.getMockGiverIdInput());
        donationInput.setCooperative(CooperativeIdInputMock.getMockCooperativeIdInput());

        return donationInput;
    }
}

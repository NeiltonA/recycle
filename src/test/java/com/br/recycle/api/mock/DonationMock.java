package com.br.recycle.api.mock;

import com.br.recycle.api.model.AvailabilityDays;
import com.br.recycle.api.model.AvailabilityPeriod;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.model.Storage;

import java.util.List;

/**
 * Classe de mock para atender os cenários de Doação de resposta
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class DonationMock {

    public static Donation getMockDonation() {
        Donation donation = new Donation();
        donation.setDonorUserName("Juca Silva");
        donation.setAmount(10L);
        donation.setStorage(Storage.PET_BOTTLE);
        donation.setAvailabilityDays(AvailabilityDays.WEEKENDS);
        donation.setAvailabilityPeriod(AvailabilityPeriod.MORNING);
        donation.setGiver(GiverIdMock.getMockGiver());
        donation.setCooperative(CooperativeIdMock.getMockCooperative());

        return donation;
    }

    public static List<Donation> getMockCollectionDonation() {
        Donation donation1 = new Donation();
        donation1.setDonorUserName("Juca Silva");
        donation1.setAmount(10L);
        donation1.setStorage(Storage.PET_BOTTLE);
        donation1.setAvailabilityDays(AvailabilityDays.WEEKENDS);
        donation1.setAvailabilityPeriod(AvailabilityPeriod.MORNING);
        donation1.setGiver(GiverIdMock.getMockGiver());
        donation1.setCooperative(CooperativeIdMock.getMockCooperative());

        Donation donation2 = new Donation();
        donation2.setDonorUserName("Caio Bastos");
        donation2.setAmount(50L);
        donation2.setStorage(Storage.BARREL);
        donation2.setAvailabilityDays(AvailabilityDays.EVERY_DAY);
        donation2.setAvailabilityPeriod(AvailabilityPeriod.ANY_TIME);
        donation2.setGiver(GiverIdMock.getMockGiver());
        donation2.setCooperative(CooperativeIdMock.getMockCooperative());

        return List.of(donation1, donation2);
    }
}


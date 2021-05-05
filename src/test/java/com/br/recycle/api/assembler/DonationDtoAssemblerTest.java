package com.br.recycle.api.assembler;

import com.br.recycle.api.mock.DonationInputMock;
import com.br.recycle.api.mock.DonationMock;
import com.br.recycle.api.model.*;
import com.br.recycle.api.payload.DonationDtoOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Classe de teste para validar os cenários de transformação de dados
 * de entrada e dados de saida da aplicação do Recycle, com relação
 * aos dados da Donation
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
@ExtendWith(SpringExtension.class)
public class DonationDtoAssemblerTest {

    ModelMapper modelMapper;

    @Spy
    private DonationDtoAssembler donationDtoAssembler;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
    }

    /**
     * Método de teste contendo o cenário de transformação de objeto
     * de entrada para o objeto de dominio da aplicação.
     */
    @Test
    public void testToDomainObjectSucess() {
        Donation donation = donationDtoAssembler.toDomainObject(DonationInputMock.getMockDonationInput());
        assertNotNull(donation);
        assertEquals("Juca Silva", donation.getDonorUserName());
        assertEquals(Long.valueOf(10), donation.getAmount());
        assertEquals(Storage.PET_BOTTLE, donation.getStorage());
        assertEquals(AvailabilityDays.WEEKENDS, donation.getAvailabilityDays());
        assertEquals(AvailabilityPeriod.MORNING, donation.getAvailabilityPeriod());
        assertEquals(DonationStatus.CREATED, donation.getStatus());
        assertEquals(Long.valueOf(1), donation.getGiver().getId());
        assertEquals(Long.valueOf(1), donation.getCooperative().getId());
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação
     */
    @Test
    public void testToModelSucess() {
        DonationDtoOut donationDtoOut = donationDtoAssembler.toModel(DonationMock.getMockDonation());
        assertNotNull(donationDtoOut);
        assertEquals("Juca Silva", donationDtoOut.getDonorUserName());
        assertEquals(Long.valueOf(10), donationDtoOut.getAmount());
        assertEquals(Storage.PET_BOTTLE, donationDtoOut.getStorage());
        assertEquals(AvailabilityDays.WEEKENDS, donationDtoOut.getAvailabilityDays());
        assertEquals(AvailabilityPeriod.MORNING, donationDtoOut.getAvailabilityPeriod());
        assertEquals(DonationStatus.CREATED, donationDtoOut.getStatus());
        assertEquals(Long.valueOf(1), donationDtoOut.getGiver().getId());
        assertEquals(Long.valueOf(1), donationDtoOut.getCooperative().getId());
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação que será uma resposta
     * de uma lista
     */
    @Test
    public void testToCollectionModelSucess() {
        List<DonationDtoOut> donationDtoOuts = donationDtoAssembler.toCollectionModel(DonationMock.getMockCollectionDonation());
        assertNotNull(donationDtoOuts);
        assertEquals("Juca Silva", donationDtoOuts.get(0).getDonorUserName());
        assertEquals(Long.valueOf(10), donationDtoOuts.get(0).getAmount());
        assertEquals(Storage.PET_BOTTLE, donationDtoOuts.get(0).getStorage());
        assertEquals(AvailabilityDays.WEEKENDS, donationDtoOuts.get(0).getAvailabilityDays());
        assertEquals(AvailabilityPeriod.MORNING, donationDtoOuts.get(0).getAvailabilityPeriod());
        assertEquals(DonationStatus.CREATED, donationDtoOuts.get(0).getStatus());
        assertEquals(Long.valueOf(1), donationDtoOuts.get(0).getGiver().getId());
        assertEquals(Long.valueOf(1), donationDtoOuts.get(0).getCooperative().getId());

        assertEquals("Caio Bastos", donationDtoOuts.get(1).getDonorUserName());
        assertEquals(Long.valueOf(50), donationDtoOuts.get(1).getAmount());
        assertEquals(Storage.BARREL, donationDtoOuts.get(1).getStorage());
        assertEquals(AvailabilityDays.EVERY_DAY, donationDtoOuts.get(1).getAvailabilityDays());
        assertEquals(AvailabilityPeriod.ANY_TIME, donationDtoOuts.get(1).getAvailabilityPeriod());
        assertEquals(DonationStatus.CREATED, donationDtoOuts.get(1).getStatus());
        assertEquals(Long.valueOf(1), donationDtoOuts.get(1).getGiver().getId());
        assertEquals(Long.valueOf(1), donationDtoOuts.get(1).getCooperative().getId());

    }
}
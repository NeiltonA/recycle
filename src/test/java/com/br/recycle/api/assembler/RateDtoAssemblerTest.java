package com.br.recycle.api.assembler;

import com.br.recycle.api.mock.RateInputMock;
import com.br.recycle.api.mock.RateMock;
import com.br.recycle.api.model.Rate;
import com.br.recycle.api.payload.RateDtoOut;
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
 * aos dados da rate
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 04/05/2021
 */
@ExtendWith(SpringExtension.class)
public class RateDtoAssemblerTest {

    ModelMapper modelMapper;

    @Spy
    private RateDtoAssembler rateDtoAssembler;

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
        Rate rate = rateDtoAssembler.toDomainObject(RateInputMock.getMockRateInput());
        assertNotNull(rate);
        assertEquals(Long.valueOf(9), rate.getNote());
        assertEquals("Excelente", rate.getComment());
        assertEquals(Long.valueOf(1), rate.getCooperative().getId());
        assertEquals(Long.valueOf(1), rate.getGiver().getId());
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação
     */
    @Test
    public void testToModelSucess() {
        RateDtoOut rateDtoOut = rateDtoAssembler.toModel(RateMock.getMockRate());
        assertNotNull(rateDtoOut);
        assertEquals(Long.valueOf(1), rateDtoOut.getId());
        assertEquals(Long.valueOf(1), rateDtoOut.getNote());
        assertEquals("Excelente", rateDtoOut.getComment());

        assertEquals(Long.valueOf(1), rateDtoOut.getCooperative().getId());
        assertEquals("Companhia teste Brasil", rateDtoOut.getCooperative().getCompanyName());
        assertEquals("Dono Teste", rateDtoOut.getCooperative().getResponsibleName());
        assertEquals("12345678900", rateDtoOut.getCooperative().getCpfResponsible());
        assertEquals("12345678900098", rateDtoOut.getCooperative().getCnpj());

        assertEquals(Long.valueOf(1), rateDtoOut.getCooperative().getUser().getId());
        assertEquals("Caio Henrique Bastos", rateDtoOut.getCooperative().getUser().getName());
        assertEquals("caioTeste@teste.com", rateDtoOut.getCooperative().getUser().getEmail());
        assertEquals("11983512000", rateDtoOut.getCooperative().getUser().getCellPhone());
        assertEquals("Teste", rateDtoOut.getCooperative().getUser().getIndividualRegistration());
        assertEquals("D", rateDtoOut.getCooperative().getUser().getFlowIndicator());
        assertEquals(Boolean.TRUE, rateDtoOut.getCooperative().getUser().getActive());

        assertEquals(Long.valueOf(1), rateDtoOut.getGiver().getId());
        assertEquals("Teste", rateDtoOut.getGiver().getCode());
        assertEquals(Long.valueOf(1), rateDtoOut.getGiver().getUser().getId());
        assertEquals("Caio Henrique Bastos", rateDtoOut.getGiver().getUser().getName());
        assertEquals("caioTeste@teste.com", rateDtoOut.getGiver().getUser().getEmail());
        assertEquals("11983512000", rateDtoOut.getGiver().getUser().getCellPhone());
        assertEquals("Teste", rateDtoOut.getGiver().getUser().getIndividualRegistration());
        assertEquals("D", rateDtoOut.getGiver().getUser().getFlowIndicator());
        assertEquals(Boolean.TRUE, rateDtoOut.getGiver().getUser().getActive());
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação que será uma resposta
     * de uma lista
     */
    @Test
    public void testToCollectionModelSucess() {
        List<RateDtoOut> rateDtoOuts = rateDtoAssembler.toCollectionModel(RateMock.getMockCollectionRate());
        assertNotNull(rateDtoOuts);
        assertEquals(Long.valueOf(1), rateDtoOuts.get(0).getId());
        assertEquals(Long.valueOf(1), rateDtoOuts.get(0).getNote());
        assertEquals("Excelente", rateDtoOuts.get(0).getComment());

        assertEquals(Long.valueOf(1), rateDtoOuts.get(0).getCooperative().getId());
        assertEquals("Companhia teste Brasil", rateDtoOuts.get(0).getCooperative().getCompanyName());
        assertEquals("Dono Teste", rateDtoOuts.get(0).getCooperative().getResponsibleName());
        assertEquals("12345678900", rateDtoOuts.get(0).getCooperative().getCpfResponsible());
        assertEquals("12345678900098", rateDtoOuts.get(0).getCooperative().getCnpj());

        assertEquals(Long.valueOf(1), rateDtoOuts.get(0).getCooperative().getUser().getId());
        assertEquals("Caio Henrique Bastos", rateDtoOuts.get(0).getCooperative().getUser().getName());
        assertEquals("caioTeste@teste.com", rateDtoOuts.get(0).getCooperative().getUser().getEmail());
        assertEquals("11983512000", rateDtoOuts.get(0).getCooperative().getUser().getCellPhone());
        assertEquals("Teste", rateDtoOuts.get(0).getCooperative().getUser().getIndividualRegistration());
        assertEquals("D", rateDtoOuts.get(0).getCooperative().getUser().getFlowIndicator());
        assertEquals(Boolean.TRUE, rateDtoOuts.get(0).getCooperative().getUser().getActive());

        assertEquals(Long.valueOf(1), rateDtoOuts.get(0).getGiver().getId());
        assertEquals("Teste", rateDtoOuts.get(0).getGiver().getCode());
        assertEquals(Long.valueOf(1), rateDtoOuts.get(0).getGiver().getUser().getId());
        assertEquals("Caio Henrique Bastos", rateDtoOuts.get(0).getGiver().getUser().getName());
        assertEquals("caioTeste@teste.com", rateDtoOuts.get(0).getGiver().getUser().getEmail());
        assertEquals("11983512000", rateDtoOuts.get(0).getGiver().getUser().getCellPhone());
        assertEquals("Teste", rateDtoOuts.get(0).getGiver().getUser().getIndividualRegistration());
        assertEquals("D", rateDtoOuts.get(0).getGiver().getUser().getFlowIndicator());
        assertEquals(Boolean.TRUE, rateDtoOuts.get(0).getGiver().getUser().getActive());
    }
}

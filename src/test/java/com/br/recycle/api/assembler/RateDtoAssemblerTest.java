package com.br.recycle.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.recycle.api.mock.RateMock;
import com.br.recycle.api.model.Rate;
import com.br.recycle.api.payload.RateDtoOut;

/**
 * Classe de teste para validar os cenários de transformação de dados
 * de entrada e dados de saida da aplicação do Recycle, com relação
 * aos dados de Avaliação
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 06/07/2021
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
     * Método responsável por validar o cenário de teste onde transformar
     * um dado de entrada para uma entidade de entrada.
     */
    @Test
    public void testToDomainObjectSuccess() {
    	Rate rate = rateDtoAssembler.toDomainObject(RateMock.getMockRateInput());
    	assertNotNull(rate);
    	assertEquals("Excelente", rate.getComment());
    	assertEquals(Long.valueOf(9), rate.getNote());
    	assertEquals(Long.valueOf(1), rate.getCooperative().getId());
    	assertEquals(Long.valueOf(1), rate.getGiver().getId());
    }

    /**
     * Método responsável por validar o cenário de teste onde transformar
     * um dado de entidade para os dado de saída da aplicação.
     */
    @Test
    public void testToModelSuccess() {
    	RateDtoOut rateDtoOut = rateDtoAssembler.toModel(RateMock.getMockModel());
    	assertNotNull(rateDtoOut);
    	assertEquals(Long.valueOf(1), rateDtoOut.getId());
    	assertEquals("Excelente", rateDtoOut.getComment());
    	assertEquals(Long.valueOf(9), rateDtoOut.getNote());
    	assertEquals(Long.valueOf(1), rateDtoOut.getCooperative().getId());
    	assertEquals(Long.valueOf(1), rateDtoOut.getGiver().getId());
    }

    /**
     * Método responsável por validar o cenário de teste onde transformar
     * uma collection de entidade para uma collection de entidade de saída da aplicação.
     */
    @Test
    public void testToCollectionModelSuccess() {
    	List<RateDtoOut> rateDtoOut = rateDtoAssembler.toCollectionModel(RateMock.getMockCollectionModel());
    	assertNotNull(rateDtoOut);
    	assertEquals(Long.valueOf(1), rateDtoOut.get(0).getId());
    	assertEquals("Excelente", rateDtoOut.get(0).getComment());
    	assertEquals(Long.valueOf(9), rateDtoOut.get(0).getNote());
    	assertEquals(Long.valueOf(1), rateDtoOut.get(0).getCooperative().getId());
    	assertEquals(Long.valueOf(1), rateDtoOut.get(0).getGiver().getId());
    }
}

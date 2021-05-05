package com.br.recycle.api.assembler;

import com.br.recycle.api.mock.CooperativeInputMock;
import com.br.recycle.api.mock.CooperativeMock;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.payload.CooperativeDtoOut;
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
 * aos dados da Cooperative
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
@ExtendWith(SpringExtension.class)
public class CooperativeDtoAssemblerTest {

    ModelMapper modelMapper;

    @Spy
    private CooperativeDtoAssembler cooperativeDtoAssembler;

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
        Cooperative cooperative = cooperativeDtoAssembler.toDomainObject(CooperativeInputMock.getMockCooperativeInput());
        assertNotNull(cooperative);
        assertEquals("Recycle do brasil LTDA", cooperative.getCompanyName());
        assertEquals("Juca do brasil LTDA", cooperative.getResponsibleName());
        assertEquals("29090890025", cooperative.getCpfResponsible());
        assertEquals("52288720000106", cooperative.getCnpj());
        assertEquals(Long.valueOf(1), cooperative.getUser().getId());
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação
     */
    @Test
    public void testToModelSucess() {
        CooperativeDtoOut cooperativeDtoOut = cooperativeDtoAssembler.toModel(CooperativeMock.getMockCooperative());
        assertNotNull(cooperativeDtoOut);
        assertEquals("Recycle do brasil LTDA", cooperativeDtoOut.getCompanyName());
        assertEquals("Juca do brasil LTDA", cooperativeDtoOut.getResponsibleName());
        assertEquals("29090890025", cooperativeDtoOut.getCpfResponsible());
        assertEquals("52288720000106", cooperativeDtoOut.getCnpj());
        assertEquals(Long.valueOf(1), cooperativeDtoOut.getUser().getId());
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação que será uma resposta
     * de uma lista
     */
    @Test
    public void testToCollectionModelSucess() {
        List<CooperativeDtoOut> cooperativeDtoOuts = cooperativeDtoAssembler.toCollectionModel(CooperativeMock.getMockCollectionCooperative());
        assertNotNull(cooperativeDtoOuts);
        assertEquals("Recycle do brasil LTDA", cooperativeDtoOuts.get(0).getCompanyName());
        assertEquals("Juca do brasil LTDA", cooperativeDtoOuts.get(0).getResponsibleName());
        assertEquals("29090890025", cooperativeDtoOuts.get(0).getCpfResponsible());
        assertEquals("52288720000106", cooperativeDtoOuts.get(0).getCnpj());
        assertEquals(Long.valueOf(1), cooperativeDtoOuts.get(0).getUser().getId());

        assertEquals("Recycle do USA LTDA", cooperativeDtoOuts.get(1).getCompanyName());
        assertEquals("Juca do USA LTDA", cooperativeDtoOuts.get(1).getResponsibleName());
        assertEquals("45090890030", cooperativeDtoOuts.get(1).getCpfResponsible());
        assertEquals("25288720000250", cooperativeDtoOuts.get(1).getCnpj());
        assertEquals(Long.valueOf(1), cooperativeDtoOuts.get(1).getUser().getId());
    }
}
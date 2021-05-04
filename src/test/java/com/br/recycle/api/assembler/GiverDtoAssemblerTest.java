package com.br.recycle.api.assembler;

import com.br.recycle.api.mock.GiverMock;
import com.br.recycle.api.payload.GiverDtoOut;
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
 * aos dados da giver
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
@ExtendWith(SpringExtension.class)
public class GiverDtoAssemblerTest {

    ModelMapper modelMapper;

    @Spy
    private GiverDtoAssembler giverDtoAssembler;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação
     */
    @Test
    public void testToModelSucess() {
        GiverDtoOut giverDtoOut = giverDtoAssembler.toModel(GiverMock.getMockGiver());
        assertNotNull(giverDtoOut);
        assertEquals(Long.valueOf(1), giverDtoOut.getId());
        assertEquals(Long.valueOf(1), giverDtoOut.getUser().getId());
        assertEquals("Caio Henrique Bastos", giverDtoOut.getUser().getName());
        assertEquals("caioTeste@teste.com", giverDtoOut.getUser().getEmail());
        assertEquals("11983512000", giverDtoOut.getUser().getCellPhone());
        assertEquals("D", giverDtoOut.getUser().getFlowIndicator());
        assertEquals(Boolean.TRUE, giverDtoOut.getUser().getActive());
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação que será uma resposta
     * de uma lista de endereço
     */
    @Test
    public void testToCollectionModelSucess() {
        List<GiverDtoOut> donationDtoOuts = giverDtoAssembler.toCollectionModel(GiverMock.getMockCollectionMock());
        assertNotNull(donationDtoOuts);
        assertEquals(Long.valueOf(1), donationDtoOuts.get(0).getId());
        assertEquals(Long.valueOf(1), donationDtoOuts.get(0).getUser().getId());
        assertEquals("Caio Henrique Bastos", donationDtoOuts.get(0).getUser().getName());
        assertEquals("caioTeste@teste.com", donationDtoOuts.get(0).getUser().getEmail());
        assertEquals("11983512000", donationDtoOuts.get(0).getUser().getCellPhone());
        assertEquals("D", donationDtoOuts.get(0).getUser().getFlowIndicator());
        assertEquals(Boolean.TRUE, donationDtoOuts.get(0).getUser().getActive());
    }
}

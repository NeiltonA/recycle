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

import com.br.recycle.api.mock.GiverMock;
import com.br.recycle.api.payload.GiverDtoOut;

/**
 * Classe de teste para validar os cenários de transformação de dados
 * de entrada e dados de saida da aplicação do Recycle, com relação
 * aos dados de Giver
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 05/07/2021
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
     * Método responsável por validar o cenário de teste onde transformar
     * um dado de entidadde para modelo de resposta.
     */
    @Test
    public void testToModelSuccess() {
    	GiverDtoOut giverDtoOut = giverDtoAssembler.toModel(GiverMock.getMockToModel());
    	assertNotNull(giverDtoOut);
    	assertEquals(Long.valueOf(1), giverDtoOut.getId());
    	assertEquals(Long.valueOf(1), giverDtoOut.getUser().getId());
    }
    
    /**
     * Método responsável por validar o cenário de teste onde transformar
     * um dado de entidadde para modelo de resposta.
     */
    @Test
    public void testToCollectionModelSuccess() {
    	List<GiverDtoOut> giverDtoOut = giverDtoAssembler.toCollectionModel(GiverMock.getMockToCollectionModel());
    	assertNotNull(giverDtoOut);
    	assertEquals(Long.valueOf(1), giverDtoOut.get(0).getId());
    	assertEquals(Long.valueOf(1), giverDtoOut.get(0).getUser().getId());
    }
}

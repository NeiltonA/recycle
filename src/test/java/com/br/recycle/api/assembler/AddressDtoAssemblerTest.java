package com.br.recycle.api.assembler;

import com.br.recycle.api.mock.AddressInputMock;
import com.br.recycle.api.mock.AddressMock;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.payload.AddressDtoOut;
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
 * de entrada e dados de saida da aplicação do Recycle com relação aos dados
 * de Address.
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
@ExtendWith(SpringExtension.class)
public class AddressDtoAssemblerTest {

    ModelMapper modelMapper;

    @Spy
    AddressDtoAssembler addressDtoAssembler;

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

        Address address = addressDtoAssembler.toDomainObject(AddressInputMock.getMockAddressInput());
        assertNotNull(address);
        assertEquals("Rua Doutor Teste", address.getStreet());
        assertEquals("123", address.getNumber());
        assertEquals("AP123", address.getComplement());
        assertEquals("06766300", address.getZipCode());
        assertEquals("Jundiai", address.getNeighborhood());
        assertEquals("SP", address.getState());
        assertEquals("São Paulo", address.getCity());
        assertEquals(Long.valueOf(1), address.getUser().getId());
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação
     */
    @Test
    public void testToModelSucess() {

        AddressDtoOut addressDtoOut = addressDtoAssembler.toModel(AddressMock.getMockAddress());
        assertNotNull(addressDtoOut);
        assertEquals("Rua Doutor Teste", addressDtoOut.getStreet());
        assertEquals("123", addressDtoOut.getNumber());
        assertEquals("AP123", addressDtoOut.getComplement());
        assertEquals("06766300", addressDtoOut.getZipCode());
        assertEquals("Jundiai", addressDtoOut.getNeighborhood());
        assertEquals("SP", addressDtoOut.getState());
        assertEquals("São Paulo", addressDtoOut.getCity());
        assertEquals(Long.valueOf(1), addressDtoOut.getUser().getId());
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação que será uma resposta
     * de uma lista
     */
    @Test
    public void testToCollectionModelSucess() {
        List<AddressDtoOut> addressDtoOuts = addressDtoAssembler.toCollectionModel(AddressMock.getMockCollectionAddress());
        assertNotNull(addressDtoOuts);

        assertEquals("Rua Doutor Teste", addressDtoOuts.get(0).getStreet());
        assertEquals("123", addressDtoOuts.get(0).getNumber());
        assertEquals("AP123", addressDtoOuts.get(0).getComplement());
        assertEquals("06766300", addressDtoOuts.get(0).getZipCode());
        assertEquals("Jundiai", addressDtoOuts.get(0).getNeighborhood());
        assertEquals("SP", addressDtoOuts.get(0).getState());
        assertEquals("São Paulo", addressDtoOuts.get(0).getCity());
        assertEquals(Long.valueOf(1), addressDtoOuts.get(0).getUser().getId());

        assertEquals("Rua Carlos Teste", addressDtoOuts.get(1).getStreet());
        assertEquals("456", addressDtoOuts.get(1).getNumber());
        assertEquals("05680200", addressDtoOuts.get(1).getZipCode());
        assertEquals("Pq Piramaba", addressDtoOuts.get(1).getNeighborhood());
        assertEquals("RJ", addressDtoOuts.get(1).getState());
        assertEquals("Rio de Janeiro", addressDtoOuts.get(1).getCity());
        assertEquals(Long.valueOf(1), addressDtoOuts.get(1).getUser().getId());
    }
}
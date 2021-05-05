package com.br.recycle.api.assembler;

import com.br.recycle.api.mock.UserInputMock;
import com.br.recycle.api.mock.UserMock;
import com.br.recycle.api.model.Flow;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.UserDtoOut;
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
 * aos dados da user
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 04/05/2021
 */
@ExtendWith(SpringExtension.class)
public class UserDtoAssemblerTest {

    ModelMapper modelMapper;

    @Spy
    private UserDtoAssembler userDtoAssembler;

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
        User user = userDtoAssembler.toDomainObject(UserInputMock.getMockUserInput());
        assertNotNull(user);
        assertEquals("Teste Silva", user.getName());
        assertEquals("teste@recycle.com", user.getEmail());
        assertEquals("11 99999999", user.getCellPhone());
        assertEquals("10364680032", user.getIndividualRegistration());
        assertEquals(Flow.D, user.getFlowIndicator());
        assertEquals(Boolean.TRUE, user.getActive());
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação
     */
   @Test
    public void testToModelSucess() {
        UserDtoOut userDtoOut = userDtoAssembler.toModel(UserMock.getMockUserDto());
        assertNotNull(userDtoOut);
        assertEquals(Long.valueOf(1), userDtoOut.getId());
        assertEquals("Caio Henrique Bastos", userDtoOut.getName());
        assertEquals("caioTeste@teste.com", userDtoOut.getEmail());
        assertEquals("11983512000", userDtoOut.getCellPhone());
        assertEquals("Teste", userDtoOut.getIndividualRegistration());
        assertEquals("D", userDtoOut.getFlowIndicator());
    }

    /**
     * Método de teste contendo o cenário de transformação do objeto
     * de dominio para o objeto de saída da aplicação que será uma resposta
     * de uma lista
     */
   @Test
    public void testToCollectionModelSucess() {
       List<UserDtoOut> userDtoOuts = userDtoAssembler.toCollectionModel(UserMock.getMockCollectionUser());
       assertNotNull(userDtoOuts);
       assertEquals(Long.valueOf(1), userDtoOuts.get(0).getId());
       assertEquals("Caio Henrique Bastos", userDtoOuts.get(0).getName());
       assertEquals("caioTeste@teste.com", userDtoOuts.get(0).getEmail());
       assertEquals("11983512000", userDtoOuts.get(0).getCellPhone());
       assertEquals("Teste", userDtoOuts.get(0).getIndividualRegistration());
       assertEquals("D", userDtoOuts.get(0).getFlowIndicator());

    }
}


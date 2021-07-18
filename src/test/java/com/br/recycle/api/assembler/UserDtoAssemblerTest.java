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

import com.br.recycle.api.mock.UserMock;
import com.br.recycle.api.model.Flow;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.UserDtoOut;

/**
 * Classe de teste para validar os cenários de transformação de dados
 * de entrada e dados de saida da aplicação do Recycle, com relação
 * aos dados de Usuario
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 06/07/2021
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
     * Método responsável por validar o cenário de teste onde transformar
     * um dado de entrada para uma entidade de entrada.	
     */
    @Test
    public void testToDomainObjectSuccess() {
    	User user = userDtoAssembler.toDomainObject(UserMock.getDomainObject());
    	assertNotNull(user);	
    	assertEquals("Teste Silva", user.getName());
    	assertEquals("teste@recycle.com", user.getEmail());
    	assertEquals("11999999999", user.getCellPhone());
    	assertEquals("10364680032", user.getIndividualRegistration());
    	assertEquals("admin123", user.getPassword());
    	assertEquals("admin123", user.getConfirmPassword());
    	assertEquals(Flow.D, user.getFlowIndicator());
    	assertEquals(Boolean.TRUE, user.getActive());
    }
    
    /**
     * Método responsável por validar o cenário de teste onde transformar
     * um dado de entidade para os modelos de saída
     */
    @Test
    public void testToModelSuccess() {
    	UserDtoOut userDtoOut = userDtoAssembler.toModel(UserMock.getMockUserDto());
    	assertNotNull(userDtoOut);	
    	assertEquals("Teste Silva", userDtoOut.getName());
    	assertEquals("teste@recycle.com", userDtoOut.getEmail());
    	assertEquals("11 999999999", userDtoOut.getCellPhone());
    	assertEquals("10364680032", userDtoOut.getIndividualRegistration());
    	assertEquals("D", userDtoOut.getFlowIndicator());
    	assertEquals(Boolean.TRUE, userDtoOut.getActive());
    }

    /**
     * Método responsável por validar o cenário de teste onde transformar
     * um dado de entidade para os modelos de saída
     */
    @Test
    public void testToCollectionModelSuccess() {
    	List<UserDtoOut> userDtoOut = userDtoAssembler.toCollectionModel(UserMock.getMockCollectionModel());
    	assertNotNull(userDtoOut);	
    	assertEquals("Teste Silva", userDtoOut.get(0).getName());
    	assertEquals("teste@recycle.com", userDtoOut.get(0).getEmail());
    	assertEquals("11 999999999", userDtoOut.get(0).getCellPhone());
    	assertEquals("10364680032", userDtoOut.get(0).getIndividualRegistration());
    	assertEquals("D", userDtoOut.get(0).getFlowIndicator());
    	assertEquals(Boolean.TRUE, userDtoOut.get(0).getActive());
    }
    
    /**
     * Método responsável por validar o cenário de teste onde transformar
     * um dado de entrada para uma entidade de entrada.	
     */
    @Test
    public void testToDomainObjectDotInSuccess() {
    	User user = userDtoAssembler.toDomainObject(UserMock.getMockDtoInObject());
    	assertNotNull(user);	
    	assertEquals("Teste Silva", user.getName());
    	assertEquals("teste@recycle.com", user.getEmail());
    	assertEquals("11999999999", user.getCellPhone());
    	assertEquals("10364680032", user.getIndividualRegistration());
    	assertEquals(Boolean.TRUE, user.getActive());
    }
}

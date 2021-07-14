package com.br.recycle.api.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.br.recycle.api.exception.UserInvalidException;
import com.br.recycle.api.mock.UserMock;
import com.br.recycle.api.repository.PwRepository;

/**
 * Classe responsável por mapear os cenários de testes da classe de serviço do PWService.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 12/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class PwServiceTest {

	@Mock
	private PwRepository pwRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private PwService pwService;
	
	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(pwService, "jwtSecret", "1234");
		ReflectionTestUtils.setField(pwService, "jwtExpirationInMsPw", 123454566);
	}
	
	/**
	 * Método responsável por validar o cenário onde o usuário esquece a senha
	 * e informa um e-mail que não existe.
	 */
	@Test
	public void testForgotPasswordEntityNotFound() {
		given(pwRepository.findByEmail("teste@teste.com")).willReturn(null);
		
		assertThrows(EntityNotFoundException.class, () -> pwService.forgotPassword("teste@teste.com"));
	}
	
	/**
	 * Método responsável por validar o cenário onde o usuário tenta resetar a senha
	 * mas informa um token inválido.
	 */
	@Test
	public void testResetPasswordUserInvalidToken() {
		given(pwRepository.findByToken("123456asdfghj234tyhb")).willReturn(null);
		
		assertThrows(UserInvalidException.class, () -> pwService.resetPassword("123456asdfghj234tyhb", "123456"));
	}
	
	/**
	 * Método responsável por validar o cenário onde o usuário tenta resetar a senha
	 * mas informa um token que está expirado.
	 */
	@Test
	public void testResetPasswordUserInvalidTokenExpired() {
		given(pwRepository.findByToken("123456asdfghj234tyhb")).willReturn(UserMock.getMockUserDto());
		
		assertThrows(UserInvalidException.class, () -> pwService.resetPassword("123456asdfghj234tyhb", "123456"));
	}
	
	
}

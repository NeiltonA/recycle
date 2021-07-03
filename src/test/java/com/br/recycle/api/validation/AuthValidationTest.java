package com.br.recycle.api.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.br.recycle.api.exception.BadRequestException;

/**
 * Classe de teste responsável por carregar os cenários de
 * de teste da validação da classe de Autenticação.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 03/07/2021
 *
 */
public class AuthValidationTest {

	private static final String REFRESH_TOKEN = "tokenDeTesteParaChamada";
	
	/**
	 * Método responsável por validar com sucesso um 
	 * token informado no parâmetro para validar.
	 */
	@Test
	public void testValidateSuccess() {
		String refreshToken = AuthValidation.validate(REFRESH_TOKEN);
		assertEquals(REFRESH_TOKEN, refreshToken);
	}
	
	/**
	 * Método responsável por validar quando um valor de refreshToken
	 * é informado vazio.
	 */
	@Test
	public void testValidateValueBlanck() {
		assertThrows(BadRequestException.class, () -> AuthValidation.validate(""));
	}
	
	/**
	 * Método responsável por validar quando um valor de refreshToken
	 * é informado null.
	 */
	@Test
	public void testValidateValueNull() {
		assertThrows(BadRequestException.class, () -> AuthValidation.validate(null));
	}
	
	/**
	 * Método responsável por validar quando um valor de refreshToken
	 * é informado uma String null.
	 */
	@Test
	public void testValidateValueStringIsNull() {
		assertThrows(BadRequestException.class, () -> AuthValidation.validate("null"));
	}
}

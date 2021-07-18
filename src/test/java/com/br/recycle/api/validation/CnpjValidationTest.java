package com.br.recycle.api.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.br.recycle.api.exception.BadRequestException;

/**
 * Classe de teste responsável por validar os cenários de teste 
 * de validação do CNPJ.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 10/07/2021
 *
 */
public class CnpjValidationTest {

	/**
	 * Método responsável por validar o cenário onde é informado
	 * um CNPJ de tamaho exato e formatado.
	 */
	@Test
	public void testValidateFormatedSuccess() {
		String cnpj = CnpjValidation.validate("76.511.411/0001-03");
		assertEquals("76511411000103", cnpj);
	}
	
	/**
	 * Método responsável por validar o cenário onde é informado
	 * um CNPJ de tamaho exato.
	 */
	@Test
	public void testValidateSuccess() {
		String cnpj = CnpjValidation.validate("76511411000103");
		assertEquals("76511411000103", cnpj);
	}
	
	/**
	 * Método responsável por validar o cenário onde é informado
	 * um CNPJ de tamaho maior que 14.
	 */
	@Test
	public void testValidateBadRequestGreatherThan8() {
		assertThrows(BadRequestException.class, () -> CnpjValidation.validate("765114110001030"));
	}
	
	/**
	 * Método responsável por validar o cenário onde é informado
	 * um CNPJ de tamaho menor que 14.
	 */
	@Test
	public void testValidateBadRequestLessThan8() {
		assertThrows(BadRequestException.class, () -> CnpjValidation.validate("7651141100010"));
	}
}

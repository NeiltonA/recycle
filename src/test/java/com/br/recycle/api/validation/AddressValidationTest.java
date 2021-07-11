package com.br.recycle.api.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.br.recycle.api.exception.BadRequestException;

/**
 * Classe de teste responsável por validar os cenários de teste 
 * de validação do endereço.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 10/07/2021
 *
 */
public class AddressValidationTest {
	
	/**
	 * Método responsável por validar o cenário onde é informado
	 * um CEP de tamaho exato.
	 */
	@Test
	public void testValidateSuccess() {
		AddressValidation.validate("06766200");
	}
	
	/**
	 * Método responsável por validar o cenário onde é informado
	 * um CEP de tamaho exato quando está formatado.
	 */
	@Test
	public void testValidateFormatedSuccess() {
		AddressValidation.validate("06.766-200");
	}
	
	/**
	 * Método responsável por validar o cenário onde é informado
	 * um CEP de tamaho exato quando está formatado.
	 */
	@Test
	public void testValidateFormated2Success() {
		AddressValidation.validate("06766-200");
	}
	
	/**
	 * Método responsável por validar o cenário onde é informado
	 * um CEP de tamaho maior que 8.
	 */
	@Test
	public void testValidateBadRequestGreatherThan8() {
		assertThrows(BadRequestException.class, () -> AddressValidation.validate("067662000"));
	}
	
	/**
	 * Método responsável por validar o cenário onde é informado
	 * um CEP de tamaho menor que 8.
	 */
	@Test
	public void testValidateBadRequestLessherThan8() {
		assertThrows(BadRequestException.class, () -> AddressValidation.validate("0676620"));
	}
	
	/**
	 * Método responsável por validar o cenário onde é informado
	 * um CEP de tamaho exato mas informado com letra
	 */
	@Test
	public void testValidateBadRequestWithLetter() {
		assertThrows(BadRequestException.class, () -> AddressValidation.validate("0676620O"));
	}
}

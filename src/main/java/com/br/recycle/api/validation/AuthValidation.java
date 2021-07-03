package com.br.recycle.api.validation;

import org.apache.commons.lang3.StringUtils;

import com.br.recycle.api.exception.BadRequestException;

/**
 * Classe responsável por realizar as validações dos campos de entrada 
 * da rota de Autenticação.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 03/07/2021
 *
 */
public class AuthValidation {

	private static final String MENSAGEM_CAMPO_NULO = "O campo '%s' não pode ser nulo ou vazio.";
	private static final String REFRESH_TOKEN = "refresh_token";
	
	/**
	 * Método responsável por realizar as validações. Caso não seja lançado 
	 * a exceção, é retornado o valor do RefreshToken
	 * 
	 * @param {@code String} - refreshToken
	 * @return {@code String} - O valor do refreshToken
	 */
	public static String validate(String refreshToken) {
		
		validateBlanckValue(refreshToken);
		
		return refreshToken;
	}

	/**
	 * Método responsável por validar se o campo de 'refreshToken' está nulo ou vazio.
	 * Caso esteja com um valor errado, é lançado uma exceção com o valor de <b>Bad Request - 400</b>
	 * 
	 * @param {@code String} - refreshToken
	 */
	private static void validateBlanckValue(String refreshToken) {
		if (StringUtils.isBlank(refreshToken) || refreshToken.equals("null")) {
			throw new BadRequestException(String.format(MENSAGEM_CAMPO_NULO, REFRESH_TOKEN));
		}
	}

}

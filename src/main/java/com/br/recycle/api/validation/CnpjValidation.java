package com.br.recycle.api.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.br.recycle.api.exception.BadRequestException;
import com.br.recycle.api.utils.RegexCharactersUtils;

/**
 * Classe responsável por validar os dados de entrada com relação 
 * ao endereço.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 09/07/2021
 *
 */
public class CnpjValidation {

	public static String validate(String cnpj) {
		
		cnpj = RegexCharactersUtils.removeSpecialCharacters(cnpj);
		Pattern pattern = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
		Matcher matcher = pattern.matcher(cnpj); 
	    if (!matcher.matches()) {
			throw new BadRequestException("O CNPJ informado está com o tamanho inválido.");
		}
	    
	    return cnpj;
	}
}

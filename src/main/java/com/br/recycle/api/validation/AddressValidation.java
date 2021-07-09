package com.br.recycle.api.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.br.recycle.api.exception.BadRequestException;

/**
 * Classe responsável por validar os dados de entrada com relação 
 * ao endereço.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 09/07/2021
 *
 */
public class AddressValidation {

	public static void validate(String zipcode) {
		
	    Pattern pattern_zipcode = Pattern.compile("(^\\d{5}-\\d{3}|^\\d{2}.\\d{3}-\\d{3}|\\d{8})");
	    Matcher matcherZipcode = pattern_zipcode.matcher(zipcode);
	    
	    if (!matcherZipcode.matches()) {
			throw new BadRequestException("O CEP informado está com o tamanho inválido.");
		}
	}
}

package com.br.recycle.api.validation.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe responsável por validar os dados de entrada do os caracteres do cep.
 * 
 * @author caiobastos
 *
 */
public class ZipcodeValidator implements ConstraintValidator<ZipcodeValidation, String> {

	@Override
	public void initialize(ZipcodeValidation zipcodeValidation) {

	}

	/**
	 * Método responsável por validar os cenários onde é informado o valor do cep.
	 * Só pode ser aceito quando informado 8 caracteres em números, aceito formatado
	 * ou não e letras. Caso não seja informado, será valido. Porque não é um dado
	 * obrigatório.
	 */
	@Override
	public boolean isValid(String zipcode, ConstraintValidatorContext context) {

		if (Objects.nonNull(zipcode)) {
			return zipcode.matches("(^\\d{5}-\\d{3}|^\\d{2}.\\d{3}-\\d{3}|\\d{8})");
		}

		return true;
	}

}

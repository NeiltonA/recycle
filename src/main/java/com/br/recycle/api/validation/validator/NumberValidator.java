package com.br.recycle.api.validation.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe responsável por validar os dados de entrada de números.
 * 
 * @author caiobastos
 *
 */
public class NumberValidator implements ConstraintValidator<NumberValidation, String> {

	@Override
	public void initialize(NumberValidation numberValidation) {

	}
	
	/**
	 * Método responsável por validar os cenários onde só pode ser informado
	 * números positivos..
	 */
	@Override
	public boolean isValid(String number, ConstraintValidatorContext context) {

		if (Objects.nonNull(number)) {
			return number.matches("^\\d+$");
		}

		return true;
	}

}


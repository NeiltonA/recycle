package com.br.recycle.api.validation.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe responsável por validar os dados de entrada do os caracteres do
 * estados.
 * 
 * @author caiobastos
 *
 */
public class StateValidator implements ConstraintValidator<StateValidation, String> {

	@Override
	public void initialize(StateValidation stateValidation) {

	}

	/**
	 * Método responsável por validar os cenários onde é informado o valor do
	 * estado. Só pode ser aceito quando informado 2 caracteres e letras. Caso não
	 * seja informado, será valido. Porque não é um dado obrigatório.
	 */
	@Override
	public boolean isValid(String state, ConstraintValidatorContext context) {

		if (Objects.nonNull(state)) {
			return state.matches("^[A-Z]{2}$");
		}

		return true;
	}

}

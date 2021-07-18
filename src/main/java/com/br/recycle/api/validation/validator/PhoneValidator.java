package com.br.recycle.api.validation.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.br.recycle.api.utils.RegexCharactersUtils;

/**
 * Classe responsável por validar os dados de entrada do celular.
 * 
 * @author caiobastos
 *
 */
public class PhoneValidator implements ConstraintValidator<PhoneValidation, String> {

	@Override
	public void initialize(PhoneValidation celularValidation) {

	}

	/**
	 * Método responsável por validar se o celular está presente. Se ele for
	 * informado. É validado se é um DDD válido e se o celular foi informado de
	 * forma correta. É opcional o mascaramento. Se ele não foi informado, é
	 * retornado true, porque a validação não é obrigatório, para um dado não
	 * informado.
	 */
	@Override
	public boolean isValid(String cellphone, ConstraintValidatorContext context) {

		if (Objects.nonNull(cellphone)) {
			cellphone = RegexCharactersUtils.removeSpecialCharacters(cellphone);
			return cellphone.matches(
					"^\\(?(?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$");
		}

		return true;
	}
}

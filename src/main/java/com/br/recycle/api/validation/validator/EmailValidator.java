package com.br.recycle.api.validation.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe responsável por validar os dados de entrada do email.
 * 
 * @author caiobastos
 *
 */
public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

	@Override
	public void initialize(EmailValidation emailValidation) {

	}
	
	/**
	 * Método responsável por validar os cenários onde é informado um e-mail.
	 * Válida se o contêm o '@' e se não contém caracteres especiais.
	 * Caso nao for informado, ele será garantido que é válido.
	 */
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {

		if (Objects.nonNull(email)) {
			return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		}

		return true;
	}

}

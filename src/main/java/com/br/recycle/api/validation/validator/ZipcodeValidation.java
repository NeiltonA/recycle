package com.br.recycle.api.validation.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ZipcodeValidator.class)
@Target({ FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ZipcodeValidation {

	String message() default "O CEP informado está inválido. Por favor informe um cep com 8 dígitos.";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}

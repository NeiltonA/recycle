package com.br.recycle.api.validation.validator;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = StateValidator.class)
@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StateValidation {

	String message() default "O valor do estado está inválido. Deve ser informado apenas 2 carácteres.";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}

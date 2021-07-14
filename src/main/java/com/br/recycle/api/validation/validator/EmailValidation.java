package com.br.recycle.api.validation.validator;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidation {

	String message() default "O e-mail informado está inválido, informe um e-mail válido.";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
